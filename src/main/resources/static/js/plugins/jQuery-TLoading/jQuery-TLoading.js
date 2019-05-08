;(function($, window, document,undefined) {
    $.extend({
        showTLoading: function() {
            $('body').append('<div class="TLloading" style="width: 100%;height: 100%;background-color: #9ea6b9;opacity: 0.2;position: absolute;z-index: 150000000;top: 0;left:0;"></div>' +
                '<div class="sk-circle TLloading">' +
                '<div class="sk-circle1 sk-child"></div>' +
                '<div class="sk-circle2 sk-child"></div>' +
                '<div class="sk-circle3 sk-child"></div>' +
                '<div class="sk-circle4 sk-child"></div>' +
                '<div class="sk-circle5 sk-child"></div>' +
                '<div class="sk-circle6 sk-child"></div>' +
                '<div class="sk-circle7 sk-child"></div>' +
                '<div class="sk-circle8 sk-child"></div>' +
                '<div class="sk-circle9 sk-child"></div>' +
                '<div class="sk-circle10 sk-child"></div>' +
                '<div class="sk-circle11 sk-child"></div>' +
                '<div class="sk-circle12 sk-child"></div>' +
                '</div>');
        },
        hideTLoading: function(){
            $('.TLloading').remove();
        }
    });
})(jQuery, window, document);