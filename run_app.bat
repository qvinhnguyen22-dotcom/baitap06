@echo off
chcp 65001 > nul
echo ======================================================================
echo    KHỞI ĐỘNG DỰ ÁN SPRING BOOT BÀI TẬP 06 - THYMELEAF CRUD CATEGORY
echo    Sinh viên: Nguyễn Quang Vinh - MSSV: 24133074
echo    Trường: Đại học Sư phạm Kỹ thuật TP.HCM (HCMUTE)
echo ======================================================================
set JAVA_HOME=C:\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
echo Đang khởi chạy ứng dụng tại: http://localhost:8088 ...
mvn spring-boot:run
pause
