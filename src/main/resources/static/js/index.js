$(document).ready(function () {

    var index = 0;
    $('.country-chooser').select2();
    getCountries();
    getAllData(index);
});

function getCountries() {
    var errMsg = "There was an error retrieving countries";
    $.get("/country/get-all", function (data) {
        if (data.status !== 200) {
            $("#errorMessage").text(errMsg);
            $("#errorCode").text(data.status);
            $("#errorModal").modal();
        } else {
            for (var i in data.countries) {
                countries.push(
                    {
                        value: data.countries[i].name
                    }
                );

            }
            console.log(countries);

        }
    })
        .fail(function (err) {
            $("#errorMessage").text(errMsg);
            $("#errorCode").text(err.status);
            $("#errorModal").modal();
        });
}

function getAllData(from) {
    var errMsg = "There was an error retrieving the data";
    $.get("/content-item/get-all-content-items/" + from + "/" + config.batchSize, function (data) {
        if (data.status !== 200) {
            $("#errorMessage").text(errMsg);
            $("#errorCode").text(data.status);
            $("#errorModal").modal();
        } else {
            fillDashboard(from, data);
            initDashboard();
        }
    })
        .fail(function (err) {
            $("#errorMessage").text(errMsg);
            $("#errorCode").text(err.status);
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
    console.log(data);
    $("#dashboardTableBody").html("");
    var to = from + config.batchSize;
    var showingTo = $("#showingTo");
    $("#showingFrom").text(from + 1);
    showingTo.text(parseInt(to));
    if (from === 0) {
        $("#previousButton").hide();
    } else {
        $("#previousButton").show();
    }
    $("#outOf").text(data.outOf);
    var numOfPages = data.contentItems.length / config.batchSize;
    if (data.contentItems.length % config.batchSize > 0) {
        numOfPages++;
    }
    $(".page-item").not(".active").remove();
    var last = $("#paginationList #firstPage");
    for (var i = 2; i <= numOfPages; i++) {
        last.after('<li class="page-item"><a href="#" class="page-link">' + i + '</a></li>');
    }
    $.get("../htmls/item-content.html", function (htmlItem) {
        if (data.contentItems.length % config.batchSize !== 0) {
            $("#showingTo").text(from + data.contentItems.length);
        }
        for (var i in data.contentItems) {
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