cmake_minimum_required(VERSION 3.4.1)

# 引入指定目录下的CMakeLists.txt
add_subdirectory(src/main/cpp/librtmp)

add_library(
             native-lib

             SHARED

             src/main/cpp/native-lib.cpp
             src/main/cpp/VideoChannel.cpp
             src/main/cpp/AudioChannel.cpp)

include_directories(src/main/cpp/include)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/src/main/cpp/libs/${ANDROID_ABI}")
target_link_libraries(
                       native-lib
                       rtmp
                       x264
                       faac
            log)