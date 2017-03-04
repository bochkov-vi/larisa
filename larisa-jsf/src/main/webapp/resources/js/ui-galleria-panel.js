/**
 * Created by home on 02.03.17.
 */
$('.ui-galleria-panel').each(function () {
    var $this = $(this);
    var $img = $this.children('img');

    // контейнер длинна\высота
    var cW = $this.width(),
        cH = $this.height();

    // изображение длинна\высота
    var iW = $img.width(),
        iH = $img.height();

    // отношение длинны с высотой контейнера\изображ
    var cRatio = cW / cH,
        iRatio = iW / iH;

    if (cRatio < iRatio) { //этот блок
        $img.height(cH);
        var margin=-((cH/iH)*iW-cW)/2 //(новая ширина картинки минус ширина контейнера) пополам
        $img.css('margin-left',margin)

    } else {
        var margin=-((cW/iW)*iH-cH)/2
        $img.width(cW);
        $img.css('margin-top',margin)
    }

    // аналог console.log в данной ситуации
    // $this.append(cRatio + ' > ' + iRatio + ' > ' + corr);

});
