$(function() {
    var url = document.URL;
    var baseNum = 0;
    if(url.indexOf('hiveview-') > -1) {
        baseNum = 1;
    }
    var actions = url.split('/');
    var menu = actions[3 + baseNum];
    var subMenu = actions[4 + baseNum];
    if(menu.length > 0 && subMenu.length > 0) {
        if (subMenu.indexOf("?") > -1) {
            subMenu = subMenu.split('?')[0];
        }
        if (subMenu.indexOf('#')) {
            subMenu = subMenu.split('#')[0];
        }

        if (menu === 'index') {
            subMenu = menu;
        }
        var selector = '.' + menu;
        var selectorSub = '.' + subMenu + ' a';
    }
    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this);
        $next = $this.next();
        $next.slideToggle();
        $this.parent().toggleClass('open');
        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
            if(menu.length > 0 && subMenu.length > 0) {
                $(selectorSub).toggleClass('active');
            }
        }
        if(menu.length > 0 && subMenu.length > 0) {
            $(selectorSub).toggleClass('active');
        }
    };
    new Accordion($('#accordion'), false);
    if(menu.length > 0 && subMenu.length > 0) {
        $(selector).next().slideToggle();
        $(selector).parent().toggleClass('open');
        $(selectorSub).toggleClass('active');
    }
});
var Accordion = function(el, multiple) {
    this.el = el || {};
    this.multiple = multiple || false;

    // Variables privadas
    var links = this.el.find('.link');
    // Event
    links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
};