@ECHO OFF

CALL :check_create_dir bin
CALL :check_create_dir .mantra
CALL :download_mantra_jar
CALL :save_run_script
CALL :check_java
EXIT /B %ERRORLEVEL%

:check_create_dir
if not exist %USERPROFILE%\%~1 mkdir %USERPROFILE%\%~1
EXIT /B 0

:download_mantra_jar
set tmpfile=%TEMP%\maninst.tmp
curl -s https://api.github.com/repos/deetree/releasetest/releases/latest | findstr download.*jar > %tmpfile%
set /p line=<%tmpfile%
set result=%line:~30%
curl -L -H "Accept: application/zip" %result% -o %USERPROFILE%\.mantra\mantra.jar
EXIT /B 0

:save_run_script
(
    echo @echo off
    echo java --version >nul 2>nul
    echo if NOT %errorlevel%==0 (
    echo     echo Java has not been found
    echo ) else (
    echo     if "%~1"=="update" (
    echo         if "%~2"=="" (
    echo         call :update
    echo         ) else (
    echo         call :jar
    echo         )
    echo     ) else (
    echo         call :jar
    echo     )
    echo )
    echo exit
    echo
    echo :jar
    echo echo java -jar %USERPROFILE%\.mantra\mantra.jar
    echo exit
    echo
    echo :update
    echo set tmpfile=%TEMP%\maninst.tmp
    echo curl -s https://api.github.com/repos/deetree/releasetest/releases/latest | findstr download.*jar > %tmpfile%
    echo set /p line=<%tmpfile%
    echo set result=%line:~30%
    echo curl -L -H "Accept: application/zip" %result% -o %USERPROFILE%\.mantra\mantra.jar
    echo exit
) > %USERPROFILE%\bin\mantra
EXIT /B 0

:check_java
java --version >nul 2>nul
if NOT %errorlevel%==0 echo Java has not been found
EXIT /B 0
