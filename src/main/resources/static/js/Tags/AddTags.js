function addTag() {
        // event.preventDefault();

        var tagInput = document.getElementById('tag');
        var tag = tagInput.value.trim();

        if (tag !== '') {
            var tagList = document.getElementById('tagList');
            var listItem = document.createElement('li');
            listItem.innerHTML = `
                        <input type="hidden" name="tags[]" value="${tag}">
                        <label>${tag}</label>
                    `;
            tagList.appendChild(listItem);

            tagInput.value = '';
        }
        console.log(tagList.children.length);


    updateHiddenInput()
}

function updateHiddenInput() {
    var tagLabels = document.querySelectorAll('#tagList label');
    var tagValues = Array.from(tagLabels).map(label => label.innerText);

    var inputTags = document.getElementById('inputTags');
    inputTags.value = tagValues.join(',');

    console.log(inputTags)
}

console.log("hello from add tag")