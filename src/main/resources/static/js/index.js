
$(document).ready(() => {
    $('.toggle').click(function () {
        $('nav').slideToggle();
    });

    $('textarea').each(function () {
        this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    }).on('input', function () {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    });


    $(window).scroll(function () {
        if ($(this).scrollTop() > 150) {
            $('nav').addClass('sticky');
            $('ul.menu > li > a').addClass('smaller');
        } else {
            $('nav').removeClass('sticky');
            $('ul.menu > li > a').removeClass('smaller');
        }
    })
});


window.onload = function() {
    let images =document.querySelectorAll(".thump img");
    for(let ima of images)
        ima.onclick = function() {
            let d = document.getElementById("main");
            d.src = this.src;
        }
}