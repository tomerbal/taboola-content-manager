$(document).ready(function () {

    var index = 0;
    getCountries();
    getBrands();
    getAllData(index);

    $('#insertForm').submit(function (event) {
        event.preventDefault();
        var data = {};
        var insertInput = $(".insert-input");
        insertInput.each(function () {
            data[$(this).attr("name")] = $(this).val().length > 0 ? $(this).val() : $(this).attr("val");
        });
        $.ajax({
            url: '/content-item/create',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            cache: false,
            success: function () {
                getAllData(index);
                insertInput.each(function () {
                    $(this).val("");
                });
                $("#genericMessage").text("Item created successfully");
                $("#genericCode").text(200);
                $("#genericModal").modal();
            },
            error: function (data) {
                $("#genericMessage").text("There was an error inserting the item");
                $("#genericCode").text(data.status);
                $("#genericModal").modal();
            }
        });
    });
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
            $("#brandChooser").select2({
                data: brands,
                placeholder: brands[0].text
            }).on('select2:select', function (e) {
                var data = e.params.data;
                brandInput.attr("val", data.text);
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
            $("#countryChooser").select2({
                data: countries,
                placeholder: countries[0].text
            }).on('select2:select', function (e) {
                var data = e.params.data;
                countryInput.attr("val", data.text);
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
    console.log("Getting  /content-item/get-all-content-items/" + from);
    $.get("/content-item/get-all-content-items/" + from + "/" + config.batchSize, function (data) {
        console.log(data);
        if (data.status !== 200) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(data.status);
            $("#genericModal").modal();
        } else {
            fillDashboard(from, data);
            initDashboard();
        }
    })
        .fail(function (err) {
            $("#genericMessage").text(errMsg);
            $("#genericCode").text(err.status);
            $("#errorModal").modal();
        });
}

function initDashboard() {
    // Activate tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Select/Deselect checkboxes
    var checkbox = $('table tbody input[type="checkbox"]');
    $("#selectAll").click(function () {
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
            });
        } else {
            checkbox.each(function () {
                this.checked = false;
            });
        }
    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
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
    if (from + 1 === 1){
        $("#firstPage").addClass("active");
    }
    for (var i = 2; i <= numOfPages; i++) {
        if (i === from + 1) {
            last.after('<li class="page-item active"><a class="page-link" onclick="paging(this)">' + i + '</a></li>');
        } else {
            last.after('<li class="page-item"><a class="page-link" onclick="paging(this)">' + i + '</a></li>');
        }
    }

    $.get("../htmls/item-content.html", function (htmlItem) {
        for (var i in data.contentItems) {
            console.log(i);
            var item = data.contentItems[i];
            var $jQueryItemContent = $($.parseHTML(htmlItem));
            var itemContentInputCheckbox = $jQueryItemContent.find(".itemContentInputCheckbox");
            itemContentInputCheckbox.attr("id", "checkbox" + i);
            var itemContentInputLabel = $jQueryItemContent.find(".itemContentInputLabel");
            itemContentInputLabel.attr("for", "checkbox" + i);
            $jQueryItemContent.find(".country").text(item.countryName);
            $jQueryItemContent.find(".brand").text(item.brand);
            $jQueryItemContent.find(".title").text(item.title);
            $jQueryItemContent.find(".img img").attr("src", item.img);
            $("#dashboardTableBody").append($jQueryItemContent);
        }
    });
}


function paging(element) {
    $(".pagination").find(".active").removeClass("active");
    //   $(this).parent().addClass("active");
    console.log("clicked");
    getAllData(parseInt(element.innerText) - 1);
}
