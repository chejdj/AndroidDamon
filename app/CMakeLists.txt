cmake_minimum_required(VERSION 3.4.1)

#### 添加自己写的库
add_library(keeplive
        SHARED
        src/main/cpp/keepLive.cpp)



find_library( # Sets the name of the path variable.
        log-lib
        log)


target_link_libraries( # Specifies the target library.
        keeplive
        ${log-lib})