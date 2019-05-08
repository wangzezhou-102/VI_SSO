function isIE() {
    if(navigator.userAgent.indexOf("MSIE") > 0 || (navigator.userAgent.toLowerCase().indexOf("trident") > -1 && navigator.userAgent.indexOf("rv") > -1)) {
        return true;
    }
    return false;
}

function isFirefox() {
    if(navigator.userAgent.indexOf("Firefox") > 0) {
        return true;
    }
    return false;
}

function isChrome() {
    if(navigator.userAgent.indexOf("Chrome") > 0) {
        return true;
    }
    return false;
}