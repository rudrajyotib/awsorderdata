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
        },
        error : function(response)
        {
            alert('order search error. please try again.');
        }
    });
}