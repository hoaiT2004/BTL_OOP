function loadCates() {
    fetch("data/categories.json").then(res => res.json()).then(data => {
        let h = "";
        for (let c of data)
            h += `<li><a class="cate" href="/${c.tag}">${c.name}</a></li>`;
        let e = document.getElementById("menu");
        if (e !== null)
            e.innerHTML += h;
    })

}

function loadInfo() {
    fetch("data/chung.json").then(res => res.json()).then(data => {
        let pt = "";
        let mb = "";
        let nc = "";
        for (let p of data)
            switch (p.cateId) {
                case 1: {
                    pt += `
                    <div class="item">
                                    <div class="image"><img src="images/${p.image}" style="width: 100%;" /></div>
                                    <div class="info">
                                        <div class="main-title"><a href="tro${p.id}.html">${p.title}</a></div>
                                        <div class="main-info">
                                            <div class="price">${p.price} Triệu/tháng</div>
                                            <div>${p.dientich}m2</div>
                                            <div>${p.address}</div>
                                        </div>
                                        <div>${p.info}
                                        </div>
                                        <div>
                                            <a href="tro${p.id}.html">Xem thông tin chi tiết <i class="fas fa-arrow-right"></i></a>
                                        </div>
                                    </div>
                                </div>
                    `;
                    let e = document.getElementById("phongtro");
                    if (e !== null)
                        e.innerHTML = pt;
                };
                    break;
                case 2: {
                    nc += `
                    <div class="item">
                                    <div class="image"><img src="images/${p.image}" style="width: 100%;" /></div>
                                    <div class="info">
                                        <div class="main-title"><a href="nha${p.id}.html">${p.title}</a></div>
                                        <div class="main-info">
                                            <div class="price"><strong>${p.price}</strong> Triệu/tháng</div>
                                            <div>${p.dientich}m2</div>
                                            <div>${p.address}</div>
                                        </div>
                                        <div>${p.info}
                                        </div>
                                        <div>
                                            <a href="nha${p.id}.html">Xem thông tin chi tiết <i class="fas fa-arrow-right"></i></a>
                                        </div>
                                    </div>
                                </div>
                    `;
                    let e = document.getElementById("nguyencan");
                    if (e !== null)
                        e.innerHTML = nc;
                };
                    break;
                case 3: {
                    mb += `
                    <div class="item">
                                    <div class="image"><img src="images/${p.image}" style="width: 100%;" /></div>
                                    <div class="info">
                                        <div class="main-title"><a href="matbang${p.id}.html">${p.title}</a></div>
                                        <div class="main-info">
                                            <div class="price">${p.price} Triệu/tháng</div>
                                            <div>${p.dientich}m2</div>
                                            <div>${p.address}</div>
                                        </div>
                                        <div>${p.info}
                                        </div>
                                        <div>
                                            <a href="matbang${p.id}.html">Xem thông tin chi tiết <i class="fas fa-arrow-right"></i></a>
                                        </div>
                                    </div>
                                </div>
                    `;
                    let e = document.getElementById("matbang");
                    if (e !== null)
                        e.innerHTML = mb;
                };
                    break;

            }

    })
}



window.onload = function () {
    loadInfo();


}