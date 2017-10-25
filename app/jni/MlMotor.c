//
// Created by mx on 01/07/2017.
//

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class cn_ml_tech_mx_mlservice_MlMotor */

#ifndef _Included_cn_ml_tech_mx_mlservice_MlMotor
#define _Included_cn_ml_tech_mx_mlservice_MlMotor
#ifdef __cplusplus
extern "C" {
#endif
#include <stdint.h>
#include <sys/ioctl.h>

#include <string.h>
#include <android/log.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>



/*寄存器数据结构*/
struct reg_val
{
    uint32_t reg;//寄存器地址
    uint32_t  val;//寄存器数据
};

typedef struct _TINY_MOTOR
{
    uint8_t  num;     //电机号
    uint8_t  dir;       //电机方向

    uint16_t speed;       //电机速度
    uint16_t acc_time;      //加速时间
    uint16_t u16WaveNum;      //added by XuXiang 2015.10.12
}TINY_MOTOR;

typedef struct REPORT_DATA  //added by XuXiang 2015.5.5
{
    union
    {
        struct reg_val  motorReg;
        struct
        {
            uint8_t u8MotorState[8];
        };
        struct _TINY_MOTOR tinyMotor;
    }MOTOR;
    uint8_t dataType; //  1**:返回寄存器值(100 or 108:8位寄存器,116:16位寄存器，132：32位寄存器)  1：返回电机状>态
}Report_Data;

#define MOTOR_IOC_MAGIC   'x'

#define MOTORCMD_MOTOR_CONTROL _IOWR(MOTOR_IOC_MAGIC,4,Report_Data)
#define MOTORCMD_READ_REG      _IOWR(MOTOR_IOC_MAGIC,1,Report_Data)//电机读
#define MOTORCMD_WRITE_REG     _IOWR(MOTOR_IOC_MAGIC,2,Report_Data)//电机写
#define MOTORCMD_MOTOR_QUERY _IOWR(MOTOR_IOC_MAGIC,6,Report_Data)//电机控制
#define MOTORCMD_MOTOR_RESETONE _IOWR(MOTOR_IOC_MAGIC,5,Report_Data)//电机单独复
#define MOTORCMD_LIGHT_ON    _IOWR(MOTOR_IOC_MAGIC,7,Report_Data)//开激光
#define MOTORCMD_LIGHT_OFF   _IOWR(MOTOR_IOC_MAGIC,8,Report_Data)//关激光
#define MOTORCMD_CLIENT_PID   _IOWR(MOTOR_IOC_MAGIC,9,Report_Data)//Translate PI
#define MOTORCMD_AUTOCARREYCONNECT  _IOWR(MOTOR_IOC_MAGIC,10,Report_Data)//关激>光
#define MOTORCMD_QUERYWAVENUM  _IOWR(MOTOR_IOC_MAGIC,11,Report_Data)//added by XuXiang 2015.10.12 query wave num after motor run
#define MOTORCMD_CATCHMOTOR  _IOWR(MOTOR_IOC_MAGIC,12,Report_Data)//added by XuXiang 2015.12.17,catch motor,Not moved by man
#define MOTORCMD_RELEASEMOTOR  _IOWR(MOTOR_IOC_MAGIC,13,Report_Data)//added by XuXiang 2015.12.17,release motor,Can moved by man
#define MOTORCMD_LIGHTSIGNALON  _IOWR(MOTOR_IOC_MAGIC,14,Report_Data)//added by XuXiang 2015.12.17,Use light signal
#define MOTORCMD_LIGHTSIGNALOFF  _IOWR(MOTOR_IOC_MAGIC,15,Report_Data)//added by XuXiang 2015.12.17,Not use light signal
#define MOTORCMD_TOOMANYBOTTLE  _IOWR(MOTOR_IOC_MAGIC,16,Report_Data)//added by XuXiang 2015.12.17,if too many bottle to stop push bottle

#define MOTORCMD_LED_SPEC                       _IOWR(MOTOR_IOC_MAGIC,20,Report_Data)//
#define MOTORCMD_LED_CARREY_HAVE_BOTTLE         _IOWR(MOTOR_IOC_MAGIC,21,Report_Data)//
#define MOTORCMD_LED_ROBOT_MOVE                 _IOWR(MOTOR_IOC_MAGIC,22,Report_Data)//
#define MOTORCMD_LED_CATCH_BOTTLE               _IOWR(MOTOR_IOC_MAGIC,23,Report_Data)//
#define MOTORCMD_LED_PRESS_BOTTLE               _IOWR(MOTOR_IOC_MAGIC,24,Report_Data)//
#define MOTORCMD_LED_PRESS_FINISH               _IOWR(MOTOR_IOC_MAGIC,25,Report_Data)//
#define MOTORCMD_LED_CARREY_BOTTLE_CONNECT      _IOWR(MOTOR_IOC_MAGIC,26,Report_Data)//
#define MOTORCMD_LED_PUSH_BOTTLE                _IOWR(MOTOR_IOC_MAGIC,27,Report_Data)//
#define MOTORCMD_LED_ROTATE                     _IOWR(MOTOR_IOC_MAGIC,28,Report_Data)//
#define MOTORCMD_LED_STOP_BOTTLE                _IOWR(MOTOR_IOC_MAGIC,29,Report_Data)//
#define MOTORCMD_LED_LIGHT                      _IOWR(MOTOR_IOC_MAGIC,30,Report_Data)//
#define MOTORCMD_LED_CHECK                      _IOWR(MOTOR_IOC_MAGIC,31,Report_Data)//
#define MOTORCMD_LED_CARREY_START                       _IOWR(MOTOR_IOC_MAGIC,32,Report_Data)
#define MOTORCMD_LED_CARREY_EMPTY                       _IOWR(MOTOR_IOC_MAGIC,33,Report_Data)

#define MOTORCMD_LED_COMPUTER_RUNNING                   _IOWR(MOTOR_IOC_MAGIC,34,Report_Data)
#define MOTORCMD_LED_POWER                              _IOWR(MOTOR_IOC_MAGIC,35,Report_Data)

#define MOTORCMD_LED_SPEC_OFF                           _IOWR(MOTOR_IOC_MAGIC,40,Report_Data)//
#define MOTORCMD_LED_CARREY_HAVE_BOTTLE_OFF             _IOWR(MOTOR_IOC_MAGIC,41,Report_Data)//
#define MOTORCMD_LED_ROBOT_MOVE_OFF                     _IOWR(MOTOR_IOC_MAGIC,42,Report_Data)//
#define MOTORCMD_LED_CATCH_BOTTLE_OFF                   _IOWR(MOTOR_IOC_MAGIC,43,Report_Data)//
#define MOTORCMD_LED_PRESS_BOTTLE_OFF                   _IOWR(MOTOR_IOC_MAGIC,44,Report_Data)//
#define MOTORCMD_LED_PRESS_FINISH_OFF                   _IOWR(MOTOR_IOC_MAGIC,45,Report_Data)//
#define MOTORCMD_LED_CARREY_BOTTLE_CONNECT_OFF          _IOWR(MOTOR_IOC_MAGIC,46,Report_Data)//
#define MOTORCMD_LED_PUSH_BOTTLE_OFF                    _IOWR(MOTOR_IOC_MAGIC,47,Report_Data)//
#define MOTORCMD_LED_ROTATE_OFF                         _IOWR(MOTOR_IOC_MAGIC,48,Report_Data)//
#define MOTORCMD_LED_STOP_BOTTLE_OFF                    _IOWR(MOTOR_IOC_MAGIC,49,Report_Data)//
#define MOTORCMD_LED_LIGHT_OFF                          _IOWR(MOTOR_IOC_MAGIC,50,Report_Data)//
#define MOTORCMD_LED_CHECK_OFF                          _IOWR(MOTOR_IOC_MAGIC,51,Report_Data)//
#define MOTORCMD_LED_CARREY_START_OFF                   _IOWR(MOTOR_IOC_MAGIC,52,Report_Data)
#define MOTORCMD_LED_CARREY_EMPTY_OFF                   _IOWR(MOTOR_IOC_MAGIC,53,Report_Data)

#define MOTORCMD_LED_COMPUTER_RUNNING_OFF               _IOWR(MOTOR_IOC_MAGIC,54,Report_Data)
#define MOTORCMD_LED_POWER_OFF                          _IOWR(MOTOR_IOC_MAGIC,55,Report_Data)

#define MOTORCMD_CHANGE_PWM                             _IOWR(MOTOR_IOC_MAGIC,88,Report_Data)

#define MOTORCMD_GET_VERSION                            _IOWR(MOTOR_IOC_MAGIC,99,Report_Data)
#define  LOG_TAG    "native-motor"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
int fid = -1;
/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_getCLanguageString
        (JNIEnv *env, jclass obj) {
    return (*env)->NewStringUTF(env, "This just a test for Android Studio NDK JNI developer!");
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    initMotor
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_initMotor
        (JNIEnv *env, jclass obj) {
    int fd = -1;
    fd = open("/dev/motor_control", O_RDWR);
    LOGI("open fd = %d\n", fd);
    fid = fd;
    return fd;
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    uninitMotor
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_uninitMotor
        (JNIEnv *env, jclass obj, jint fd) {
    if (fd != -1) {
        close(fd);
    }
}

void Display_ReportData_motor(Report_Data *report_data) {
    LOGI("%d, %d, %d, %d, %d, %d\n", report_data->MOTOR.tinyMotor.num,
         report_data->MOTOR.tinyMotor.dir,
         report_data->MOTOR.tinyMotor.speed,
         report_data->MOTOR.tinyMotor.acc_time,
         report_data->MOTOR.tinyMotor.u16WaveNum,
         report_data->dataType);
    LOGI("%d, %d, %d\n", report_data->MOTOR.motorReg.reg,
         report_data->MOTOR.motorReg.val,
         report_data->dataType);
}
/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorControl
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataVal;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorControl
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID numFieldId = (*env)->GetFieldID(env, motor_class, "num", "I");
    jfieldID dirFieldId = (*env)->GetFieldID(env, motor_class, "dir", "I");
    jfieldID speedFieldId = (*env)->GetFieldID(env, motor_class, "speed", "I");
    jfieldID accTimeFieldId = (*env)->GetFieldID(env, motor_class, "acc_time", "I");
    jfieldID u16WaveNumFieldId = (*env)->GetFieldID(env, motor_class, "u16WaveNum", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");
    Report_Data report_data;
    report_data.MOTOR.tinyMotor.dir = (*env)->GetIntField(env, obj_motor, dirFieldId);
    report_data.MOTOR.tinyMotor.speed = (*env)->GetIntField(env, obj_motor, speedFieldId);
    report_data.MOTOR.tinyMotor.acc_time = (*env)->GetIntField(env, obj_motor, accTimeFieldId);
    report_data.MOTOR.tinyMotor.u16WaveNum = (*env)->GetIntField(env, obj_motor, u16WaveNumFieldId);
    report_data.MOTOR.tinyMotor.num = (*env)->GetIntField(env, obj_motor, numFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);
    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_MOTOR_CONTROL, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorReadReg
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataReg;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorReadReg
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID usRegFieldId = (*env)->GetFieldID(env, motor_class, "usReg", "I");
    jfieldID ucRegValueFieldId = (*env)->GetFieldID(env, motor_class, "ucRegValue", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;
    report_data.MOTOR.motorReg.reg = (*env)->GetIntField(env, obj_motor, usRegFieldId);
    report_data.MOTOR.motorReg.val = (*env)->GetIntField(env, obj_motor, ucRegValueFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);

    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_READ_REG, &report_data);

    (*env)->SetIntField(env, obj_motor, ucRegValueFieldId, report_data.MOTOR.motorReg.val);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorWriteReg
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataReg;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorWriteReg
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID usRegFieldId = (*env)->GetFieldID(env, motor_class, "usReg", "I");
    jfieldID ucRegValueFieldId = (*env)->GetFieldID(env, motor_class, "ucRegValue", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;
    report_data.MOTOR.motorReg.reg = (*env)->GetIntField(env, obj_motor, usRegFieldId);
    report_data.MOTOR.motorReg.val = (*env)->GetIntField(env, obj_motor, ucRegValueFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);

    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_WRITE_REG, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorQueryState
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataState;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorQueryState
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID ucMotorStateFieldId = (*env)->GetFieldID(env, motor_class, "ucMotorState", "[I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;
    ioctl(fid, MOTORCMD_MOTOR_QUERY, &report_data);
    (*env)->SetIntField(env, obj_motor, dataTypeFieldId, report_data.dataType);
jintArray array = (*env)->GetObjectField(env, obj_motor, ucMotorStateFieldId);
jint a = report_data.MOTOR.u8MotorState[0];
jint b = report_data.MOTOR.u8MotorState[1];
jint c = report_data.MOTOR.u8MotorState[2];
jint d = report_data.MOTOR.u8MotorState[3];
jint e = report_data.MOTOR.u8MotorState[4];
jint f = report_data.MOTOR.u8MotorState[5];
jint g = report_data.MOTOR.u8MotorState[6];
jint h = report_data.MOTOR.u8MotorState[7];
(*env)->SetIntArrayRegion(env,array,0,1,&a);
(*env)->SetIntArrayRegion(env,array,1,1,&b);
(*env)->SetIntArrayRegion(env,array,2,1,&c);
(*env)->SetIntArrayRegion(env,array,3,1,&d);
(*env)->SetIntArrayRegion(env,array,4,1,&e);
(*env)->SetIntArrayRegion(env,array,5,1,&f);
(*env)->SetIntArrayRegion(env,array,6,1,&g);
(*env)->SetIntArrayRegion(env,array,7,1,&h);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorReset
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataVal;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorReset
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID numFieldId = (*env)->GetFieldID(env, motor_class, "num", "I");
    jfieldID dirFieldId = (*env)->GetFieldID(env, motor_class, "dir", "I");
    jfieldID speedFieldId = (*env)->GetFieldID(env, motor_class, "speed", "I");
    jfieldID accTimeFieldId = (*env)->GetFieldID(env, motor_class, "acc_time", "I");
    jfieldID u16WaveNumFieldId = (*env)->GetFieldID(env, motor_class, "u16WaveNum", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");
    Report_Data report_data;
    report_data.MOTOR.tinyMotor.dir = (*env)->GetIntField(env, obj_motor, dirFieldId);
    report_data.MOTOR.tinyMotor.speed = (*env)->GetIntField(env, obj_motor, speedFieldId);
    report_data.MOTOR.tinyMotor.acc_time = (*env)->GetIntField(env, obj_motor, accTimeFieldId);
    report_data.MOTOR.tinyMotor.u16WaveNum = (*env)->GetIntField(env, obj_motor, u16WaveNumFieldId);
    report_data.MOTOR.tinyMotor.num = (*env)->GetIntField(env, obj_motor, numFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);
    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_MOTOR_RESETONE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLightOn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLightOn
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LIGHT_ON, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLightOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLightOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LIGHT_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorClientPID
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataReg;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorClientPID
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID usRegFieldId = (*env)->GetFieldID(env, motor_class, "usReg", "I");
    jfieldID ucRegValueFieldId = (*env)->GetFieldID(env, motor_class, "ucRegValue", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;
    report_data.MOTOR.motorReg.reg = (*env)->GetIntField(env, obj_motor, usRegFieldId);
    report_data.MOTOR.motorReg.val = (*env)->GetIntField(env, obj_motor, ucRegValueFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);

    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_CLIENT_PID, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorQueryAutoCarreyConnected
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataReg;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorQueryAutoCarreyConnected
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID usRegFieldId = (*env)->GetFieldID(env, motor_class, "usReg", "I");
    jfieldID ucRegValueFieldId = (*env)->GetFieldID(env, motor_class, "ucRegValue", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;
    report_data.MOTOR.motorReg.reg = (*env)->GetIntField(env, obj_motor, usRegFieldId);
    report_data.MOTOR.motorReg.val = (*env)->GetIntField(env, obj_motor, ucRegValueFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);

    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_AUTOCARREYCONNECT, &report_data);

    (*env)->SetIntField(env, motor_class, ucRegValueFieldId, report_data.MOTOR.motorReg.val);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorQueryWaveNum
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataReg;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorQueryWaveNum
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID usRegFieldId = (*env)->GetFieldID(env, motor_class, "usReg", "I");
    jfieldID ucRegValueFieldId = (*env)->GetFieldID(env, motor_class, "ucRegValue", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;
    report_data.MOTOR.motorReg.reg = (*env)->GetIntField(env, obj_motor, usRegFieldId);
    report_data.MOTOR.motorReg.val = (*env)->GetIntField(env, obj_motor, ucRegValueFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);

    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_QUERYWAVENUM, &report_data);

    (*env)->SetIntField(env, motor_class, ucRegValueFieldId, report_data.MOTOR.motorReg.val);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorCatchMotor
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorCatchMotor
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_CATCHMOTOR, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorReleaseMotor
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorReleaseMotor
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_RELEASEMOTOR, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLightSignalOn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLightSignalOn
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LIGHTSIGNALON, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLightSignalOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLightSignalOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LIGHTSIGNALOFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorQueryTooManyBottle
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataReg;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorQueryTooManyBottle
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID usRegFieldId = (*env)->GetFieldID(env, motor_class, "usReg", "I");
    jfieldID ucRegValueFieldId = (*env)->GetFieldID(env, motor_class, "ucRegValue", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;
    report_data.MOTOR.motorReg.reg = (*env)->GetIntField(env, obj_motor, usRegFieldId);
    report_data.MOTOR.motorReg.val = (*env)->GetIntField(env, obj_motor, ucRegValueFieldId);
    report_data.dataType = (*env)->GetIntField(env, obj_motor, dataTypeFieldId);

    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_QUERYWAVENUM, &report_data);

    (*env)->SetIntField(env, motor_class, ucRegValueFieldId, report_data.MOTOR.motorReg.val);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedSpec
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedSpec
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_SPEC, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyHaveBottle
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyHaveBottle
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_HAVE_BOTTLE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedRobotMove
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedRobotMove
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_ROBOT_MOVE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCatchBottle
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCatchBottle
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CATCH_BOTTLE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPressBottle
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPressBottle
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_PRESS_BOTTLE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPressFinish
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPressFinish
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_PRESS_FINISH, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyBottleConnect
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyBottleConnect
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_BOTTLE_CONNECT, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPushBottle
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPushBottle
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_PUSH_BOTTLE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedRotate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedRotate
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_ROTATE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedStopBottle
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedStopBottle
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_STOP_BOTTLE, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedLight
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedLight
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_LIGHT, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCheck
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCheck
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CHECK, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyStart
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyStart
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_START, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyEmpty
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyEmpty
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_EMPTY, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedComputerRunning
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedComputerRunning
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_COMPUTER_RUNNING, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPower
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPower
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_POWER, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedSpecOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedSpecOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_SPEC_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyHaveBottleOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyHaveBottleOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_HAVE_BOTTLE_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedRobotMoveOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedRobotMoveOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_ROBOT_MOVE_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCatchBottleOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCatchBottleOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CATCH_BOTTLE_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPressBottleOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPressBottleOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_PRESS_BOTTLE_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPressFinishOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPressFinishOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_PRESS_FINISH_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyBottleConnectOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyBottleConnectOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_BOTTLE_CONNECT_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPushBottleOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPushBottleOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_PUSH_BOTTLE_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedRotateOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedRotateOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_ROTATE_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedStopBottleOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedStopBottleOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_STOP_BOTTLE_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedLightOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedLightOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_LIGHT_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCheckOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCheckOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CHECK_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyStartOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyStartOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_START_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedCarreyEmptyOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedCarreyEmptyOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_CARREY_EMPTY_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedComputerRunningOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedComputerRunningOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_COMPUTER_RUNNING_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorLedPowerOff
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorLedPowerOff
        (JNIEnv *env, jclass obj) {
    Report_Data report_data;
    ioctl(fid, MOTORCMD_LED_POWER_OFF, &report_data);
}

/*
 * Class:     cn_ml_tech_mx_mlservice_MlMotor
 * Method:    motorGetVersion
 * Signature: (Lcn/ml_tech/mx/mlservice/MlMotor/ReportDataReg;)V
 */
JNIEXPORT void JNICALL Java_cn_ml_1tech_mx_mlservice_MlMotor_motorGetVersion
        (JNIEnv *env, jclass obj, jobject obj_motor) {
    jclass motor_class = (*env)->GetObjectClass(env, obj_motor);
    if (motor_class == NULL) {
        LOGI("GetObjectClass failed\n");
    }
    jfieldID usRegFieldId = (*env)->GetFieldID(env, motor_class, "usReg", "I");
    jfieldID ucRegValueFieldId = (*env)->GetFieldID(env, motor_class, "ucRegValue", "I");
    jfieldID dataTypeFieldId = (*env)->GetFieldID(env, motor_class, "dataType", "I");

    Report_Data report_data;

    Display_ReportData_motor(&report_data);
    ioctl(fid, MOTORCMD_GET_VERSION, &report_data);

    (*env)->SetIntField(env, motor_class, ucRegValueFieldId, report_data.MOTOR.motorReg.val);
    //(*env)->SetObjectField(env, ucRegValueFieldId, report_data.MOTOR.motorReg.val);
}


#ifdef __cplusplus
}
#endif
#endif
