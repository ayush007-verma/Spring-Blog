function showOptions(type) {
    const filterOptions = document.getElementById('filterOptions');
    const sortOptions = document.getElementById('sortOptions');

    if (type === 'filter') {
        filterOptions.style.display = 'block';
        sortOptions.style.display = 'none';
    } else if (type === 'sort') {
        sortOptions.style.display = 'block';
        filterOptions.style.display = 'none';
    }
}

function callController(option) {
    // You can make an AJAX call or perform any other action here to communicate with your controller.
    console.log('Calling controller with option:', option);
}

function updateSortQuery() {
    var selectedOption = document.querySelector('input[name="sortOption"]:checked').value;
    console.log("selected Option : ", selectedOption)
    console.log("initial sortQuery = " , document.getElementById('sortQuery').value)
    document.getElementById('sortQuery').value = selectedOption;
    console.log("after click sortQuery = " , document.getElementById('sortQuery').value)


}
