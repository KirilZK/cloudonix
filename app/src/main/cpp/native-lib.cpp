#include <jni.h>
#include <string>
#include <ifaddrs.h>
#include <unistd.h>
#include <netdb.h>
#include <netinet/in.h>
#include <android/log.h>
#include <sys/types.h>
#include <ifaddrs.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define TAG "CloudonixNDK"


#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,    TAG, __VA_ARGS__)
extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_cloudonix_ui_IpAddressActivity_getNativeIpAddress(
        JNIEnv *env,
        jobject /* this */) {


        struct ifaddrs * ifAddrStruct=NULL;
        struct ifaddrs * ifa=NULL;
        void * tmpAddrPtr=NULL;

        getifaddrs(&ifAddrStruct);

        for (ifa = ifAddrStruct; ifa != NULL; ifa = ifa->ifa_next) {
            if (!ifa->ifa_addr) {
                continue;
            }
            if (ifa->ifa_addr->sa_family == AF_INET6) { // check it is IP6
                // is a valid IP6 Address
                char address_string6[INET6_ADDRSTRLEN];
                struct sockaddr_in6 *sa_in6 = (struct sockaddr_in6*)ifa->ifa_addr;
                struct in6_addr i_a = sa_in6->sin6_addr;
                inet_ntop(AF_INET6, &(i_a), address_string6, INET6_ADDRSTRLEN);
                //return global
                if(IN6_IS_ADDR_MC_GLOBAL(&i_a)) {
                    LOGD("Address: <%s>",address_string6);
                    return env->NewStringUTF(address_string6);
                }

            } else if(ifa->ifa_addr->sa_family == AF_INET) { // check it is IP4
                    // is a valid IP4 Address
                char address_string4[INET_ADDRSTRLEN];
                struct sockaddr_in *sa_in = (struct sockaddr_in*)ifa->ifa_addr;

                struct in_addr i_a = sa_in->sin_addr;
                inet_ntop(AF_INET, &(i_a), address_string4, INET_ADDRSTRLEN);
                LOGD("Address: <%s>", ifa->ifa_name);
                return env->NewStringUTF(address_string4);
            }
        }
        if (ifAddrStruct!=NULL)
            freeifaddrs(ifAddrStruct);
        return  env->NewStringUTF("/");




}



