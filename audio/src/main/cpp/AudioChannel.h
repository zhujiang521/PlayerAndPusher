//
// Created by Administrator on 2018/9/26.
//

#ifndef PUSHER_AUDIOCHANNEL_H
#define PUSHER_AUDIOCHANNEL_H


#include "librtmp/rtmp.h"
#include "faac.h"
#include <sys/types.h>

class AudioChannel {
    typedef void (*AudioCallback)(RTMPPacket *packet);

public:
    AudioChannel();

    ~AudioChannel();

    void setAudioEncInfo(int samplesInHZ, int channels);

    void setAudioCallback(AudioCallback audioCallback);

    int getInputSamples();

    void encodeData(int8_t *data);

    RTMPPacket* getAudioTag();
private:
    AudioCallback audioCallback;
    int mChannels;
    faacEncHandle audioCodec = 0;
    u_long inputSamples;
    u_long maxOutputBytes;
    u_char *buffer = 0;
};


#endif //PUSHER_AUDIOCHANNEL_H
