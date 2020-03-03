## 安卓一行代码实现视频直播（rtmp）以及直播推流（NDK实现）

### 前言

想写关于NDK的文章很久了，但一直不知道该写点啥，该怎么写？正好公司有一个直播和播放的需求，那好吧，就造一个视频播放和视频直播的轮子吧。那说干就干！声明一下，本篇文章不会写怎样配置NDK等问题，只是大概介绍一下思路，具体代码已经放在[Github](https://github.com/zhujiang521/PlayerAndPusher)上，大家可以下载代码自己进行定制或者直接引入进行使用。如果有幸帮到各位，那就请点一个Star，不胜感激。下面是具体使用方法：

### 引入依赖

首先在项目的build.gradle中添加以下代码：

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

然后在需要使用的module的build.gradle中添加下面的依赖：

```
dependencies {
	        implementation 'com.github.zhujiang521:PlayerAndPusher:Tag'
	}
```

### 视频播放

说起视频播放，就不得不提ffmpeg，大多数视频播放器都使用的是ffmpeg进行私有定制。本项目使用的也是ffmpeg。下面是ffmpeg的百度百科介绍：

![image-20200303180217506](/Users/zhujiang/Library/Application Support/typora-user-images/image-20200303180217506.png)

**项目中使用的不是动态库（.so），而是静态库（.a）**，这样做的好处是可以使项目大大减小，并且可以自己对代码进行修改。

实现思路大概是创建两个线程：一个用来不断获取视频信息、一个用来不断获取音频信息，然后通过相对比丢帧实现音视频同步。

太详细的代码也不在这里贴了，大家可以直接查看代码。下面说一下使用方法吧：

视频播放使用很简单，只需一行代码即可使用：

```java
 BroadcastLive.create(TVChannelActivity.this).setDataUrl("直播地址").build();
```

是不是很简单？下面给大家几个可以使用的rtmp直播流：

```java
tvBeanList.add(new TVBean("测试本地视频","/sdcard/Pictures/test.mp4"));
        tvBeanList.add(new TVBean("香港卫视","rtmp://live.hkstv.hk.lxdns.com/live/hks1"));
        tvBeanList.add(new TVBean("香港财经","rtmp://202.69.69.180:443/webcast/bshdlive-pc"));
        tvBeanList.add(new TVBean("韩国GoodTV","rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp"));
        tvBeanList.add(new TVBean("韩国朝鲜日报","rtmp://live.chosun.gscdn.com/live/tvchosun1.stream"));
        tvBeanList.add(new TVBean("美国1","rtmp://ns8.indexforce.com/home/mystream"));
        tvBeanList.add(new TVBean("美国2","rtmp://media3.scctv.net/live/scctv_800"));
        tvBeanList.add(new TVBean("美国中文电视","rtmp://media3.sinovision.net:1935/live/livestream"));
        tvBeanList.add(new TVBean("湖南卫视","rtmp://58.200.131.2:1935/livetv/hunantv"));
```

里面有的可能没法用了，湖南卫视、美国中文电视应该还可以，没有一一去验证，大家可以试试。

播放里面的实现类没有写太多的方法，大家可以自己实现。

### 直播推流

直播很常见，现在更是全民直播，哪个平台基本都有直播功能，上面有了直播播放当然也要有直播推流啊。推流的时候也分为视频和音频。大概实现过程就是将视频和音频压缩成编码发送到服务器，然后直播播放那边实时获取推上去的流，再进行音视频解码。

本项目中音频编码使用的是Open SL，视频编码使用的是H264，同上，这里也不多赘述具体实现代码，项目中都有，大家可以进入我的[Github](https://github.com/zhujiang521/PlayerAndPusher)查看。

下面是使用直播推流的方法：

```java
LiveRecording.create(this).setDataUrl("推流的地址")
                .setWidth(800)
                .setHeight(400)
                .setFps(10)
                .setBitrate(800_000)
                .build();
```

直播推流就比直播播放要多了几个方法，下面是方法的定义：

```java
/**
         * 设置成像宽度
         * @param width
         * @return
         */
        public LiveRecordingModel setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * 设置成像高度
         * @param height
         * @return
         */
        public LiveRecordingModel setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * 设置直播比特率
         * @param bitrate
         * @return
         */
        public LiveRecordingModel setBitrate(int bitrate) {
            this.bitrate = bitrate;
            return this;
        }

        /**
         * 设置FPS值
         * @param fps
         * @return
         */
        public LiveRecordingModel setFps(int fps) {
            this.fps = fps;
            return this;
        }
```

### 总结

到这里这篇文章已经接近尾声了，也不知道怎么了，明明花了很久弄的项目，真正想写文章的时候却感觉不知道该如何下手，未来的几篇文章我想把直播推流和直播播放的c++代码拿出来写一写，包括在Android Studio中项目的配置、CMake的应用以及常见的NDK错误总结一下。希望大家能够喜欢。

