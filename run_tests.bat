@echo off
echo Running Emergent.sh QA Tests
echo ============================

powershell -ExecutionPolicy Bypass -File .\run_tests.ps1

pause