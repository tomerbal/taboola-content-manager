$(document).ready(function () {

    var index = 0;
    getCountries();
    getBrands();
    getAllData(index);
    initFilter();
    initInsert();
    initWidget();
});

function getBrands() {
    var errMsg = "There was an error retrieving brands";
    $.get("/brand/get-all", function (data) {
        if (data.status !== 200) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(data.status);
            $("#genericModal").modal();
        } else {
            for (var i in data.brands) {
                brands.push(
                    {
                        text: data.brands[i],
                        id: parseInt(i)
                    }
                );
            }
            var brandInput = $("#brandInput");
            var brandInputEdit = $("#brandInputEdit");
            var brandInputFilter = $("#brandInputFilter");
            $("#brandChooser").select2({
                data: brands,
                placeholder: brands[0].text
            }).on('select2:select', function (e) {
                var data = e.params.data;
                brandInput.attr("val", data.text);
            });

            $("#brandChooserEdit").select2({
                data: brands,
                placeholder: brands[0].text
            }).on('select2:select', function (e) {
                var data = e.params.data;
                brandInputEdit.attr("val", data.text);
            });

            $("#brandChooserFilter").select2({
                data: brands,
                placeholder: brands[0].text
            }).on('change', function (e) {
                var brandIds = $(this).val();
                var brandsText = [];
                for (var i in brandIds) {
                    var brandId = parseInt(brandIds[i]);
                    for (var j in brands) {
                        var brand = brands[j];
                        if (brand.id === brandId) {
                            brandsText.push(brand.text);
                        }
                    }
                }
                brandInputFilter.attr("val", brandsText.join(";"));
            });

            brandInput.attr("val", brands[0].text);
        }
    })
        .fail(function (err) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(err.status);
            $("#genericModal").modal();
        });
}

function getCountries() {
    var errMsg = "There was an error retrieving countries";
    $.get("/country/get-all", function (data) {
        if (data.status !== 200) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(data.status);
            $("#genericModal").modal();
        } else {
            for (var i in data.countries) {
                countries.push(
                    {
                        text: data.countries[i].name,
                        id: parseInt(i)
                    }
                );
            }
            var countryInput = $("#countryInput");
            var countryInputEdit = $("#countryInputEdit");
            var countryInputFilter = $("#countryInputFilter");

            $("#countryChooser").select2({
                data: countries,
                placeholder: countries[0].text
            }).on('select2:select', function (e) {
                var data = e.params.data;
                countryInput.attr("val", data.text);
            });

            $("#countryChooserEdit").select2({
                data: countries,
                placeholder: countries[0].text
            }).on('select2:select', function (e) {
                var data = e.params.data;
                countryInputEdit.attr("val", data.text);
            });

            $("#countryChooserFilter").select2({
                data: countries,
                placeholder: countries[0].text
            }).on('change', function (e) {
                var countryIds = $(this).val();
                var countriesText = [];
                for (var i in countryIds) {
                    var countryId = parseInt(countryIds[i]);
                    for (var j in countries) {
                        var country = countries[j];
                        if (country.id === countryId) {
                            countriesText.push(country.text);
                        }
                    }
                }
                countryInputFilter.attr("val", countriesText.join(";"));
            });
            countryInput.attr("val", countries[0].text);
        }
    })
        .fail(function (err) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(err.status);
            $("#genericModal").modal();
        });
}

function getAllData(from) {
    var errMsg = "There was an error retrieving the data";
    $.get("/content-item/get-all-content-items/" + from + "/" + config.batchSize, function (data) {
        if (data.status !== 200) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(data.status);
            $("#genericModal").modal();
        } else {
            fillDashboard(from, data);
        }
    })
        .fail(function (err) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(err.status);
            $("#errorModal").modal();
        });
}

function initDashboard() {
    $('[data-toggle="tooltip"]').tooltip();

    var checkbox = $('table tbody input[type="checkbox"]');

    checkbox.click(function () {
        var checked = this.checked;
        var tr = $(this).closest("tr");
        var itemId = tr.attr("itemId");
        var country = tr.find(".country").text();
        var brand = tr.find(".brand").text();
        var title = tr.find(".title").text();
        var img = tr.find(".img img").attr("src");
        var length = Object.keys(config.chosenItems).length;
        if (checked) {
            if (length === config.widgetSize) {
                $("#genericMessage").text("Please choose up tp " + config.widgetSize + " items");
                $("#genericCode").text();
                $("#genericModal").modal();
                this.checked = false;
            } else {
                config.chosenItems[itemId] = {
                    country: country,
                    brand: brand,
                    title: title,
                    img: img
                }
            }
        } else {
            delete config.chosenItems[itemId];
        }
    });
}

function fillDashboard(from, data) {
    $("#dashboardTableBody").html("");
    var showingFrom = from * config.batchSize + 1;
    var showingTo = showingFrom + data.contentItems.length - 1;
    $("#showingTo").text(showingTo);
    $("#showingFrom").text(showingFrom);
    if (from === 0) {
        $("#previousButton").hide();
    } else {
        $("#previousButton").show();
    }
    $("#outOf").text(data.outOf);
    var numOfPages = data.outOf / config.batchSize;
    if (data.outOf % config.batchSize > 0) {
        numOfPages++;
    }
    $(".page-item").not("#firstPage").remove();
    var last = $("#paginationList #firstPage");
    if (from + 1 === 1) {
        $("#firstPage").addClass("active");
    }
    for (var i = 2; i <= numOfPages; i++) {
        if (i === from + 1) {
            last.after('<li class="page-item active"><a class="page-link" onclick="paging(this)">' + i + '</a></li>');
        } else {
            last.after('<li class="page-item"><a class="page-link" onclick="paging(this)">' + i + '</a></li>');
        }
        last = $(".page-item").last();
    }

    $.get("../htmls/item-content.html", function (htmlItem) {
        for (var i in data.contentItems) {
            var item = data.contentItems[i];
            var $jQueryItemContent = $($.parseHTML(htmlItem));
            var itemContentInputCheckbox = $jQueryItemContent.find(".itemContentInputCheckbox");
            itemContentInputCheckbox.attr("id", "checkbox" + i);
            if (config.chosenItems[item.id]) {
                itemContentInputCheckbox.prop("checked", true);
            }
            var itemContentInputLabel = $jQueryItemContent.find(".itemContentInputLabel");
            itemContentInputLabel.attr("for", "checkbox" + i);
            $jQueryItemContent.find(".country").text(item.countryName);
            $jQueryItemContent.find(".brand").text(item.brandName);
            $jQueryItemContent.find(".title").text(item.title);
            $jQueryItemContent.find(".img img").attr("src", item.img);
            $jQueryItemContent.attr("itemId", item.id);
            $("#dashboardTableBody").append($jQueryItemContent);
        }
        initDashboard();
    });
}

function paging(element) {
    $(".pagination").find(".active").removeClass("active");
    var from = parseInt(element.innerText) - 1;
    if (Object.keys(config.filters).length > 0) {
        getFilteredData(from, true)
    } else {
        getAllData(from);
    }
}
