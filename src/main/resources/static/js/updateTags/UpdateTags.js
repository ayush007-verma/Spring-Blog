// Function to add a new tag
function addNewTag() {
    console.log('inside add tag')
    var newTagInput = document.getElementById('newTag');
    var newTag = newTagInput.value.trim();

    if (newTag !== '') {
        var existingTagsList = document.getElementById('existingTags');
        var newTagItem = document.createElement('li');

        // Create a new span element
        var tagNameSpan = document.createElement('span');
        tagNameSpan.innerText = newTag;

        // Append the span to the li element
        newTagItem.appendChild(tagNameSpan);

        var updateButton = document.createElement('button');
        updateButton.type = 'button';
        updateButton.textContent = 'Update';
        updateButton.onclick = function() { updateTag(event); };
        newTagItem.appendChild(updateButton);

        var deleteButton = document.createElement('button');
        deleteButton.type = 'button';
        deleteButton.textContent = 'Delete';
        deleteButton.onclick = function() { deleteTag(event); };
        newTagItem.appendChild(deleteButton);


        existingTagsList.appendChild(newTagItem);

        newTagInput.value = '';

        // Update hidden input with all tags (both existing and new)
        updateHiddenInput();
    }
}

// Function to update hidden input with all tags
function updateHiddenInput() {
    var tagItems = document.querySelectorAll('#existingTags li span');
    var tagsArray = Array.from(tagItems).map(item => item.innerText);

    var inputTags = document.getElementById('inputTags');
    inputTags.value = tagsArray.join(',');
}


function updateTag(event) {
    var tagName = event.target.parentElement.querySelector('span').innerText;
    var updatedTagName = prompt('Update tag name:', tagName);

    if (updatedTagName !== null && updatedTagName.trim() !== '') {
        event.target.parentElement.querySelector('span').innerText = updatedTagName;
        updateHiddenInput();
    }
}

function deleteTag(event) {
    var confirmation = confirm('Are you sure you want to delete this tag?');

    if (confirmation) {
        event.target.parentElement.remove();
        updateHiddenInput();
    }
}