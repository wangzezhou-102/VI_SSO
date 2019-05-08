/*
 * Copyright 2018-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * WebSite: http://bootstrap-viewer.leftso.com
 */
$.fn.bootstrapViewer = function (options) {
    $(this).on('click', function () {
        var opts = $.extend({}, $.fn.bootstrapViewer.defaults, options);
        var viewer = $('<div class="modal fade bs-example-modal-lg text-center" id="bootstrapViewer" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >' +
                        '<div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">' +
                            '<div class="modal-content" id="imageContainer">' +
                                '<img class="carousel-inner img-responsive img-rounded img-viewer"' +
                                    'onclick="$(\'#bootstrapViewer\').modal(\'hide\');setTimeout(function(){$(\'#bootstrapViewer\').remove();},200);"' +
                                    'onmouseover="this.style.cursor=\'zoom-out\';" ' +
                                    'onmouseout="this.style.cursor=\'default\'"/>' +
                            '</div>' +
                        '</div>' +
                    '</div>');
        $('body').append(viewer);
        if ($(this).attr(opts.src)) {
            $("#bootstrapViewer").find(".img-viewer").attr("src", $(this).attr(opts.src));
            $("#bootstrapViewer").modal('show');

            $('#bootstrapViewer')
                .on('shown.bs.modal', function (e) {
                    if(opts.shownCallback){
                        opts.shownCallback();
                    }
                })
                .on('hide.bs.modal', function (e) {
                    if(opts.hideCallback){
                        opts.hideCallback();
                    }
                    $('#bootstrapViewer').remove();
                });
        } else {
            throw "图片不存在"
        }

    });

    $(this).on('mouseover', function () {
        $(this).css('cursor', 'zoom-in');
    })

}
$.fn.bootstrapViewer.defaults = {
    src: 'src'
};