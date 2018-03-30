var videoPlayer = videojs(
    document.getElementById("foo"),
    {
        techOrder: ["html5"],
        width: window.innerWidth,
        preload: "none"
    },
    function () {
        var _this = this,
            currentTimes = 0, //当前第几次
            currentTime = 0,
            INFO = {
                duration: 0, //视频总时长
                isPlay: false, //视频播放状态
                shareTimes: 10, //总中断次数
                shareTimeNodes: []
            },
            timer,
            modal;

        var promoteToShare = function () {
            return new Promise(function (resolve, reject) {
                wx.onMenuShareAppMessage({
                    title: '分享', // 分享标题
                    desc: '分享', // 分享描述
                    link: window.location.href, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                    imgUrl: '', // 分享图标
                    success: function () {
                        resolve && resolve();
                    },
                    cancel: function () {

                    }
                });
                console.log("中断");
            });
        };

        var monitor = function () {
            if (INFO.shareTimeNodes && INFO.shareTimeNodes.length) {
                //到达指定节点
                if (videoPlayer.currentTime() >= INFO.shareTimeNodes[0]) {
                    videoPlayer.pause();
                    modal = videoPlayer.createModal("分享视频，查看更多精彩!", {
                        uncloseable: true
                    });
                    INFO.shareTimeNodes.shift();
                    promoteToShare().then(
                        function () {
                            modal.close();
                            videoPlayer.play();
                            timer = setTimeout(monitor, 1000);
                        },
                        function () {
                        }
                    );
                } else {
                    timer = setTimeout(monitor, 1000);
                }
            } else {
                clearTimeout(timer);
                timer = null;
            }
        };

        this.on("loadeddata", function () {
            INFO.isPlay = true;
            INFO.duration = _this.duration();
            for (var i = 1; i <= INFO.shareTimes; i++) {
                INFO.shareTimeNodes.push(INFO.duration / INFO.shareTimes * i);
            }
            timer = setTimeout(monitor, 1000);
        });

        this.on("ended", function () {
            _this.pause();
            clearTimeout(timer);
        });

        // this.on('pause', function () {
        //     // Modals are temporary by default. They dispose themselves when they are
        //     // closed; so, we can create a new one each time the player is paused and
        //     // not worry about leaving extra nodes hanging around.
        //     var modal = this.createModal('将视频分享到三个群，继续观看!');
        //     // When the modal closes, resume playback.
        //     modal.on('modalclose', function () {
        //         _this.play();
        //     });
        // });

        // var video = this;
        // instance = new Vue({
        //     el: '#app',
        //     data: function () {
        //         return {visible: false}
        //     },
        //     created: function () {

        //     }
        // })

        // setTimeout(function () {
        //     _this.pause();
        //     alert("请分享到3个群，继续观看")
        // }, 3000);
    }
);