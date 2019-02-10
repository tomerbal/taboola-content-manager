function initFilter() {

    $("#superBrandInput").on("click", function () {
        $(this).val(this.checked);
    });

    $('#filterForm').submit(function (event) {
        event.preventDefault();
        getFilteredData(0, false);
        config.chosenItems = {};
    });
}

function getFilteredData(from, pagination) {
    config.filters.dataSize = config.batchSize;
    config.filters.from = from;

    var filterInput = $(".filter-input");
    var countriesInputVal = $("#countryInputFilter").attr("val");
    config.filters.countries = [];
    if (countriesInputVal) {
        config.filters.countries = countriesInputVal.split(";");
    }
    var brandsInputVal = $("#brandInputFilter").attr("val");
    config.filters.brands = [];
    if (brandsInputVal) {
        config.filters.brands = brandsInputVal.split(";");
    }
    config.filters.superBrand = $("#superBrandInput").val();
    $.ajax({
        url: '/content-item/filter',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(config.filters),
        dataType: 'json',
        cache: false,
        success: function (data) {
            fillDashboard(from, data);
            filterInput.each(function () {
                $(this).val("");
            });
            $("#filterItemsModal").modal('hide');
            if (!pagination) {
                $("#genericMessage").text("Item filtered successfully");
                $("#genericCode").text(200);
                $("#genericModal").modal();
            }
        },
        error: function (data) {
            $("#genericMessage").text("There was an error filtering the items");
            $("#genericCode").text(data.status);
            $("#genericModal").modal();
        }
    });
}

