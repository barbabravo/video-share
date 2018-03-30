<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="./libs/video-js.css" rel="stylesheet">
    <link rel="stylesheet" href="./assets/index.css">
</head>

<body>

<div class="video">
    <h1 class="text-center">${title}</h1>
    <video id="foo" x5-video-player-type="h5" webkit-playsinline playsinline class="vjs-matrix video-js vjs-big-play-centered" controls>
        <source src="./assets/oceans.mp4" type='video/mp4'>
        <p class="vjs-no-js">
            To view this video please enable JavaScript, and consider upgrading to a web browser that
            <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
        </p>
    </video>
</div>

<p class="hint" id="hint"></p>

<script src="./libs/weixin/jweixin-1.2.0.js"></script>
<script src="./libs/jquery/jquery.min.js"></script>
<script src="./libs/video.js"></script>
<script>
    var wxConfig = ${wxConfig};
    //    wxConfig.debug = true;
    wxConfig.jsApiList = [];
    wxConfig.jsApiList.push("onMenuShareTimeline");
    wxConfig.jsApiList.push("onMenuShareAppMessage");
    wx.config(wxConfig);

    var INFO = {
        duration: 46, //视频总时长
        isPlay: false, //视频播放状态
        shareTimeNodes: [5],
        shareTimesEach: 2    //每次中断要求分享几次
    };

    wx.ready(function () {

        document.getElementById("hint").innerHTML = "${title}视频总时长: " + INFO.duration + "; 中断时间节点: [" + INFO.shareTimeNodes.join(" | ") + "], 每次中断分享: " + INFO.shareTimesEach;

        var videoPlayer = videojs("foo", {
                    preload: "auto",
                    width: window.innerWidth - 10
                }, function () {
                    var _this = this,
                        currentShareTimers, //当前第几次分享
                        timer,  //定时器
                        modal;  //碳层

                    function promoteToShare() {
                        return new Promise(function (resolve, reject) {
                            wx.onMenuShareAppMessage({
                                title: "${title}",
                                desc: "${title}",
                                link: location.href,
                                success: function () {
                                    currentShareTimers = currentShareTimers - 1;
                                    if (currentShareTimers <= 0) {
                                        resolve && resolve();
                                    } else {
                                        modal.close();
                                        modal = videoPlayer.createModal("分享视频到" + currentShareTimers + "个群，继续观看!", {
                                            uncloseable: true
                                        });
                                    }
                                },
                                cancel: function () {

                                }
                            });
                            wx.onMenuShareTimeline({
                                title: "${title}",
                                link: location.href,
                                success: function () {
                                    currentShareTimers = currentShareTimers - 1;
                                    if (currentShareTimers <= 0) {
                                        resolve && resolve();
                                    } else {
                                        modal.close();
                                        modal = videoPlayer.createModal("分享视频到" + currentShareTimers + "个群，继续观看!", {
                                            uncloseable: true
                                        });
                                    }
                                },
                                cancel: function () {
                                }
                            });
                        });
                    };


                    function monitor() {

                        if (INFO.shareTimeNodes && INFO.shareTimeNodes.length) {
                            //到达指定节点
                            if (videoPlayer.currentTime() >= INFO.shareTimeNodes[0]) {

//                                alert(INFO.shareTimeNodes + "," + videoPlayer.currentTime())
                                //if (videoPlayer.isFullscreen()) {
                                    videoPlayer.exitFullscreen();
                                //}
                                videoPlayer.pause();
                                currentShareTimers = INFO.shareTimesEach;
                                modal = videoPlayer.createModal("分享视频到" + currentShareTimers + "个群，继续观看!", {
                                    uncloseable: true
                                });
                                INFO.shareTimeNodes.shift();
                                promoteToShare().then(function () {
                                    modal.close();
                                    videoPlayer.play();
                                    timer = setTimeout(monitor, 1000);
                                })
                            } else {
                                timer = setTimeout(monitor, 1000);
                            }
                        } else {
                            clearTimeout(timer);
                            timer = null;
                        }
                    };


                    _this.on("loadeddata", function () {
                        INFO.isPlay = true;
                        timer = setTimeout(monitor, 1000);
                    });

                    _this.on("ended", function () {
                        _this.pause();
                        clearTimeout(timer);
                        modal.close();
                    });
                }
        )
    });
</script>
</body>

</html>