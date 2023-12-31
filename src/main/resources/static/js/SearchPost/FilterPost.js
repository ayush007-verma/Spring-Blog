function showOptions(type) {
    const filterOptions = document.getElementById('filterOptions');
    const sortOptions = document.getElementById('sortOptions');

    if (type === 'filter') {
        filterOptions.style.display = 'flex';
        sortOptions.style.display = 'none';
    } else if (type === 'sort') {
        sortOptions.style.display = 'flex';
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


function updateFilterQuery() {
    var selectedOption = document.querySelector('input[name="filterOption"]:checked').value;
    console.log("selected Option : ", selectedOption)
    console.log("initial filterQuery = " , document.getElementById('filterQuery').value)
    document.getElementById('filterQuery').value = selectedOption;
    console.log("after click filterQuery = " , document.getElementById('filterQuery').value)


}
