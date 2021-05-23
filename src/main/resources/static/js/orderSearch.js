function searchOrder()
{
    let inputOrderNumber = $('#orderNumberInput').val();
    if (!inputOrderNumber || ('' === inputOrderNumber.trim()  ))
    {
        alert('Please enter a valid order number to search');
        return;
    }
    $("#searchResultPane").hide();
    $.ajax(
    {
        url:'/rest/orders/'+inputOrderNumber,
        type: 'GET',
        contentType : 'application/json',
        dataType : 'json',
        async : false,
        cache : false,
        success : function(data, response, jqXHR)
        {
            if(jqXHR.status == 204)
            {
                alert("order not found");
                return;
            }
            let orderNumber = data.orderNumber;
            let product = data.product;
            $("#searchResultPane").show();
            $("#orderNumberResult").text(orderNumber);
            $("#productResult").text(product);
            $("#orderNumberInput").val('');
        },
        error : function(response)
        {
            alert('order search error. please try again.');
        }
    });
}

function openUpdateOrderModal()
{
    var product = $("#productResult").text();
    var orderNumber = $("#orderNumberResult").text();
    $('#editedOrderProduct').val(product);
    $('#editedOrderId').val(orderNumber);
}

function updateOrderData()
{
    var updatedOrder={};
    updatedOrder.orderNumber=$('#editedOrderId').val();
    updatedOrder.product=$('#editedOrderProduct').val();

    $.ajax(
        {
            url:'/rest/orders/update',
            type: 'PUT',
            contentType : 'application/json',
            data : JSON.stringify(updatedOrder),
            dataType : 'json',
            async : false,
            cache : false,
            success : function(data, response, jqXHR)
            {
                let orderNumber = data.orderNumber;
                let product = data.product;
                $("#searchResultPane").hide();
                alert('order data updated');
            },
            error : function(response)
            {
                alert('order search error. please try again.');
            }
        });
}