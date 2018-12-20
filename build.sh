#!/bin/bash
commit_id=`git rev-parse --short HEAD`
echo ${commit_id}

servervlient_path="../Android-SDK/app/src/main/java/com/smarthome/service/net/ServerClient.java"
aesutil_path="../Android-SDK/app/src/main/java/com/smarthome/service/util/AESUtil.java"
manifest_path="app/src/main/AndroidManifest.xml"

shoppingmall_url="SHOPPINGMALL_PLACEHOLDER"
dataapi_url="DATAAPI_PLACEHOLDER"
deviceapi_url="DEVICEAPI_PLACEHOLDER"
url="SERVER_PLACEHOLDER"
udp_port="UDP_PLACEHOLDER"
tcp_port="TCP_PLACEHOLDER"
mplanet_password="PASSWORD_PLACEHOLDER"

#official
OFFICIAL_SHOPPINGMALL_URL="https:\/\/h5.youzan.com\/v2\/feature\/ckhks5vj"
OFFICIAL_DATAAPI_URL="http:\/\/app.anjugroup.cn\/api\/data"
OFFICIAL_DEVICEAPI_URL="http:\/\/app.anjugroup.cn\/api\/thirdParty\/"
OFFICIAL_STRING_URL="app.anjugroup.cn"
OFFICIAL_UDP_PORT="51051"
OFFICIAL_TCP_PORT="51050"
#OFFICIAL_MPLANET_PASSWORD="\&S1C6Z0ERDJC9M#3"
OFFICIAL_MPLANET_PASSWORD="\&amp;S1C6Z0ERDJC9M#3"

#develop
DEVELOP_SHOPPINGMALL_URL="https:\/\/h5.youzan.com\/v2\/feature\/ckhks5vj"
DEVELOP_DATAAPI_URL="http:\/\/pay.mplanet.cn\/api\/data"
DEVELOP_DEVICEAPI_URL="http:\/\/pay.mplanet.cn\/api\/thirdParty\/"
DEVELOP_STRING_URL="pay.mplanet.cn"
DEVELOP_UDP_PORT="13251"
DEVELOP_TCP_PORT="13250"
#DEVELOP_MPLANET_PASSWORD="www.mplanet.cn  "
DEVELOP_MPLANET_PASSWORD="www.mplanet.cn\&#32;\&#32;"

#build official
function buildofficial(){
sed -i "s/git_commit_id/${commit_id}/g" app/build.gradle
sed -i "s/${shoppingmall_url}/${OFFICIAL_SHOPPINGMALL_URL}/g" ${manifest_path}
sed -i "s/${dataapi_url}/${OFFICIAL_DATAAPI_URL}/g" ${manifest_path}
sed -i "s/${deviceapi_url}/${OFFICIAL_DEVICEAPI_URL}/g" ${manifest_path}
sed -i "s/${url}/${OFFICIAL_STRING_URL}/g" ${manifest_path}
sed -i "s/${udp_port}/${OFFICIAL_UDP_PORT}/g" ${manifest_path}
sed -i "s/${tcp_port}/${OFFICIAL_TCP_PORT}/g" ${manifest_path}
sed -i "s/${mplanet_password}/${OFFICIAL_MPLANET_PASSWORD}/g" ${manifest_path}
versionnumber=`grep "versionName" app/build.gradle`
versionnumber=${versionnumber#*\"}
versionnumber=${versionnumber%\"*}
./gradlew_unix clean
./gradlew_unix assembleRelease
sed -i "s/${commit_id}/git_commit_id/g" app/build.gradle
sed -i "s/${OFFICIAL_SHOPPINGMALL_URL}/${shoppingmall_url}/g" ${manifest_path}
sed -i "s/${OFFICIAL_DATAAPI_URL}/${dataapi_url}/g" ${manifest_path}
sed -i "s/${OFFICIAL_DEVICEAPI_URL}/${deviceapi_url}/g" ${manifest_path}
sed -i "s/${OFFICIAL_STRING_URL}/${url}/g" ${manifest_path}
sed -i "s/${OFFICIAL_UDP_PORT}/${udp_port}/g" ${manifest_path}
sed -i "s/${OFFICIAL_TCP_PORT}/${tcp_port}/g" ${manifest_path}
sed -i "s/${OFFICIAL_MPLANET_PASSWORD}/${mplanet_password}/g" ${manifest_path}
mv app/build/outputs/apk/app-release.apk anquanyingji_official_${versionnumber}.apk
}

#build develop
function builddevelop(){
sed -i "s/git_commit_id/${commit_id}(Beta)/g" app/build.gradle
sed -i "s/${shoppingmall_url}/${DEVELOP_SHOPPINGMALL_URL}/g" ${manifest_path}
sed -i "s/${dataapi_url}/${DEVELOP_DATAAPI_URL}/g" ${manifest_path}
sed -i "s/${deviceapi_url}/${DEVELOP_DEVICEAPI_URL}/g" ${manifest_path}
sed -i "s/${url}/${DEVELOP_STRING_URL}/g" ${manifest_path}
sed -i "s/${udp_port}/${DEVELOP_UDP_PORT}/g" ${manifest_path}
sed -i "s/${tcp_port}/${DEVELOP_TCP_PORT}/g" ${manifest_path}
sed -i "s/${mplanet_password}/${DEVELOP_MPLANET_PASSWORD}/g" ${manifest_path}
versionnumber=`grep "versionName" app/build.gradle`
versionnumber=${versionnumber#*\"}
versionnumber=${versionnumber%\"*}
./gradlew_unix clean
./gradlew_unix assembleRelease --info --debug
sed -i "s/${commit_id}(Beta)/git_commit_id/g" app/build.gradle
sed -i "s/${DEVELOP_SHOPPINGMALL_URL}/${shoppingmall_url}/g" ${manifest_path}
sed -i "s/${DEVELOP_DATAAPI_URL}/${dataapi_url}/g" ${manifest_path}
sed -i "s/${DEVELOP_DEVICEAPI_URL}/${deviceapi_url}/g" ${manifest_path}
sed -i "s/${DEVELOP_STRING_URL}/${url}/g" ${manifest_path}
sed -i "s/${DEVELOP_UDP_PORT}/${udp_port}/g" ${manifest_path}
sed -i "s/${DEVELOP_TCP_PORT}/${tcp_port}/g" ${manifest_path}
sed -i "s/${DEVELOP_MPLANET_PASSWORD}/${mplanet_password}/g" ${manifest_path}
mv app/build/outputs/apk/app-release.apk anquanyingji_develop_${versionnumber}.apk
}

builddevelop
buildofficial
