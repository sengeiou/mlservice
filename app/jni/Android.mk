LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
#bzlib模块
bzlib_files := \
    main.c

LOCAL_MODULE := libbz
LOCAL_SRC_FILES := $(bzlib_files)
include $(BUILD_STATIC_LIBRARY)
APP_PLATFORM := android-19
      APP_STL         := gnustl_shared
#bspath模块
include $(CLEAR_VARS)
LOCAL_MODULE    := main
LOCAL_STATIC_LIBRARIES := libbz #引入libbz库

include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)
# 配置自己的源文件目录和源文件后缀名
MY_FILES_PATH  :=  $(LOCAL_PATH) \
                   $(LOCAL_PATH)/../../Classes

MY_FILES_SUFFIX := %.cpp %.c

# 递归遍历目录下的所有的文件
rwildcard=$(wildcard $1$2) $(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2))

# 获取相应的源文件
MY_ALL_FILES := $(foreach src_path,$(MY_FILES_PATH), $(call rwildcard,$(src_path),*.*) )
MY_ALL_FILES := $(MY_ALL_FILES:$(MY_CPP_PATH)/./%=$(MY_CPP_PATH)%)
MY_SRC_LIST  := $(filter $(MY_FILES_SUFFIX),$(MY_ALL_FILES))
MY_SRC_LIST  := $(MY_SRC_LIST:$(LOCAL_PATH)/%=%)

# 去除字串的重复单词
define uniq =
  $(eval seen :=)
  $(foreach _,$1,$(if $(filter $_,${seen}),,$(eval seen += $_)))
  ${seen}
endef

# 递归遍历获取所有目录
MY_ALL_DIRS := $(dir $(foreach src_path,$(MY_FILES_PATH), $(call rwildcard,$(src_path),*/) ) )
MY_ALL_DIRS := $(call uniq,$(MY_ALL_DIRS))

# 赋值给NDK编译系统
LOCAL_SRC_FILES  := $(MY_SRC_LIST)
LOCAL_C_INCLUDES := $(MY_ALL_DIRS)
LOCAL_MODULE    := JniTest
LOCAL_STATIC_LIBRARIES := main
LOCAL_LDLIBS := -llog#加入log

include $(BUILD_SHARED_LIBRARY)