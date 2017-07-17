SetTitleMatchMode 2
#WinActivateForce

Loop,
{
    WinActivate, PowerPoint
    Sleep, 2000
    WinActivate, toggle_window.ahk
    Sleep, 2000
}