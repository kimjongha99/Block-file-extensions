document.addEventListener('DOMContentLoaded', function() {
    const customExtensionsContainer = document.getElementById('custom-extensions');
    const customExtensionInput = document.getElementById('custom-extension-input');
    const addCustomExtensionButton = document.getElementById('add-custom-extension-button');
    const clearFixedExtensionsButton = document.getElementById('clear-fixed-extensions-button');
    const clearCustomExtensionsButton = document.getElementById('clear-custom-extensions-button');
    const errorMessage = document.getElementById('error-message');
    const fileInput = document.getElementById('file-input');
    const uploadFileButton = document.getElementById('upload-file-button');
    let currentPage = 0;
    const pageSize = 10;
    let allowedExtensions = [];

    function fetchFixedExtensions() {
        axios.get('/api/fixed-extensions')
            .then(response => {
                if (response.data.success) {
                    const extensions = response.data.data;
                    const container = document.getElementById('fixed-extensions');
                    container.innerHTML = '';
                    extensions.forEach(extension => {
                        const html = `
                        <div>
                            <span class="px-3 py-1 bg-gray-200 rounded">${extension.name}</span>
                            <input type="checkbox" ${extension.checked ? 'checked' : ''} data-id="${extension.id}" />
                        </div>
                    `;
                        container.insertAdjacentHTML('beforeend', html);

                        // Add to allowedExtensions if checked
                        if (extension.checked) {
                            allowedExtensions.push(extension.name);
                        }
                    });

                    // Log allowedExtensions to console
                    console.log('Allowed Extensions:', allowedExtensions);

                    // Add event listener for checkboxes
                    container.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
                        checkbox.addEventListener('change', function() {
                            const id = this.getAttribute('data-id');
                            const checked = this.checked;
                            const extensionName =this.parentElement.querySelector('span').textContent;

                            axios.put(`/api/fixed-extensions/${id}`, { id: id, checked: checked })
                                .then(response => {
                                    console.log('Updated successfully', response.data);
                                    if (checked) {
                                        allowedExtensions.push(extensionName);
                                    } else {
                                        allowedExtensions = allowedExtensions.filter(ext => ext !== extensionName);
                                    }

                                    // Log allowedExtensions to console
                                    console.log('Allowed Extensions:', allowedExtensions);
                                })
                                .catch(error => {
                                    console.error('Error updating fixed extension:', error);
                                });
                        });
                    });
                }
            })
            .catch(error => {
                console.error('Error fetching fixed extensions:', error);
            });
    }

    function fetchCustomExtensions(page = 0, size = 10) {
        axios.get(`/api/custom-extensions?page=${page}&size=${size}`)
            .then(response => {
                if (response.data.success) {
                    const customExtensions = response.data.data.customExtensionResponseList;
                    const container = document.getElementById('custom-extensions');
                    container.innerHTML = '';
                    customExtensions.forEach(extension => {
                        const html = `
                            <div>
                                <span class="px-3 py-1 bg-gray-200 rounded">${extension.name}</span>
                                <input type="checkbox" ${extension.checked ? 'checked' : ''} data-id="${extension.id}" />
                                <button class="delete-custom-extension-button bg-red-500 text-white px-2 py-1 rounded" data-id="${extension.id}">삭제</button>
                            </div>
                        `;
                        container.insertAdjacentHTML('beforeend', html);
                    });

                    // Add event listener for checkboxes
                    container.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
                        if (checkbox.checked) {
                            allowedExtensions.push(checkbox.parentElement.querySelector('span').textContent);
                        }
                        checkbox.addEventListener('change', function() {
                            const extensionName = this.parentElement.querySelector('span').textContent;
                            if (this.checked) {
                                allowedExtensions.push(extensionName);
                            } else {
                                allowedExtensions = allowedExtensions.filter(ext => ext !== extensionName);
                            }

                            const id = this.getAttribute('data-id');
                            const checked = this.checked;

                            axios.put(`/api/custom-extensions/${id}`, { id: id, checked: checked })
                                .then(response => {
                                    console.log('Updated successfully', response.data);
                                })
                                .catch(error => {
                                    console.error('Error updating custom extension:', error);
                                });
                        });
                    });

                    // Add event listener for delete buttons
                    container.querySelectorAll('.delete-custom-extension-button').forEach(button => {
                        button.addEventListener('click', function() {
                            const id = this.getAttribute('data-id');

                            axios.delete(`/api/custom-extensions/${id}`)
                                .then(response => {
                                    console.log('Deleted successfully', response.data);
                                    // Remove the element from the DOM
                                    this.parentElement.remove();
                                    // Remove from allowed extensions
                                    const extensionName = this.parentElement.querySelector('span').textContent;
                                    allowedExtensions = allowedExtensions.filter(ext => ext !== extensionName);
                                })
                                .catch(error => {
                                    console.error('Error deleting custom extension:', error);
                                });
                        });
                    });

                    // Update pagination info
                    const paginationInfo = response.data.data;
                    updatePagination(paginationInfo);
                }
            })
            .catch(error => {
                console.error('Error fetching custom extensions:', error);
            });
    }

    function updatePagination(paginationInfo) {
        const paginationContainer = document.getElementById('custom-extension-page');
        paginationContainer.innerHTML = '';

        if (paginationInfo.totalPage > 1) {
            for (let i = 0; i < paginationInfo.totalPage; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i + 1;
                pageButton.className = 'px-3 py-1 bg-blue-500 text-white rounded';
                pageButton.addEventListener('click', function() {
                    fetchCustomExtensions(i, pageSize);
                });
                paginationContainer.appendChild(pageButton);
            }
        }
    }

    function addCustomExtension() {
        const extensionName = customExtensionInput.value.trim();
        if (!extensionName) {
            errorMessage.textContent = '확장자 이름을 입력해주세요.';
            return;
        }

        axios.post('/api/custom-extensions', { name: extensionName })
            .then(response => {
                if (response.data.success) {
                    const extension = response.data.data;
                    const html = `
                        <div>
                            <span class="px-3 py-1 bg-gray-200 rounded">${extension.name}</span>
                            <input type="checkbox" ${extension.checked ? 'checked' : ''} data-id="${extension.id}" />
                            <button class="delete-custom-extension-button bg-red-500 text-white px-2 py-1 rounded" data-id="${extension.id}">삭제</button>
                        </div>
                    `;
                    customExtensionsContainer.insertAdjacentHTML('beforeend', html);
                    customExtensionInput.value = '';
                    errorMessage.textContent = '';

                    // Add event listener for new checkbox
                    const newCheckbox = customExtensionsContainer.querySelector(`input[data-id="${extension.id}"]`);
                    newCheckbox.addEventListener('change', function() {
                        const id = this.getAttribute('data-id');
                        const checked = this.checked;
                        const extensionName = this.parentElement.querySelector('span').textContent;
                        if (checked) {
                            allowedExtensions.push(extensionName);
                        } else {
                            allowedExtensions = allowedExtensions.filter(ext => ext !== extensionName);
                        }

                        axios.put(`/api/custom-extensions/${id}`, { id: id, checked: checked })
                            .then(response => {
                                console.log('Updated successfully', response.data);
                            })
                            .catch(error => {
                                console.error('Error updating custom extension:', error);
                            });
                    });

                    // Add event listener for new delete button
                    const newDeleteButton = customExtensionsContainer.querySelector(`button[data-id="${extension.id}"]`);
                    newDeleteButton.addEventListener('click', function() {
                        const id = this.getAttribute('data-id');

                        axios.delete(`/api/custom-extensions/${id}`)
                            .then(response => {
                                console.log('Deleted successfully', response.data);
                                // Remove the element from the DOM
                                this.parentElement.remove();
                                // Remove from allowed extensions
                                const extensionName = this.parentElement.querySelector('span').textContent;
                                allowedExtensions = allowedExtensions.filter(ext => ext !== extensionName);
                            })
                            .catch(error => {
                                console.error('Error deleting custom extension:', error);
                            });
                    });
                }
            })
            .catch(error => {
                if (error.response && error.response.data && error.response.data.message) {
                    errorMessage.textContent = error.response.data.message;
                } else {
                    console.error('Error adding custom extension:', error);
                }
            });
    }

    function clearAllFixedExtensions() {
        axios.put('/api/fixed-extensions/clear')
            .then(response => {
                if (response.data.success) {
                    const checkboxes = document.querySelectorAll('#fixed-extensions input[type="checkbox"]');
                    checkboxes.forEach(checkbox => {
                        checkbox.checked = false;
                        const extensionName = '.' + checkbox.parentElement.querySelector('span').textContent;
                        allowedExtensions = allowedExtensions.filter(ext => ext !== extensionName);
                    });

                    // Log allowedExtensions to console
                    console.log('Allowed Extensions:', allowedExtensions);
                }
            })
            .catch(error => {
                console.error('Error clearing fixed extensions:', error);
            });
    }



    function clearAllCustomExtensions() {
        axios.put('/api/custom-extensions/clear')
            .then(response => {
                if (response.data.success) {
                    const checkboxes = document.querySelectorAll('#custom-extensions input[type="checkbox"]');
                    checkboxes.forEach(checkbox => {
                        checkbox.checked = false;
                        const extensionName = '.' + checkbox.parentElement.querySelector('span').textContent;
                        allowedExtensions = allowedExtensions.filter(ext => ext !== extensionName);
                    });
                }
            })
            .catch(error => {
                console.error('Error clearing custom extensions:', error);
            });
    }

    fetchFixedExtensions();
    fetchCustomExtensions(currentPage, pageSize);

    if (addCustomExtensionButton) {
        addCustomExtensionButton.addEventListener('click', addCustomExtension);
    }

    if (clearFixedExtensionsButton) {
        clearFixedExtensionsButton.addEventListener('click', clearAllFixedExtensions);
    }

    if (clearCustomExtensionsButton) {
        clearCustomExtensionsButton.addEventListener('click', clearAllCustomExtensions);
    }

    // Handle file upload
    uploadFileButton.addEventListener('click', function() {
        const file = fileInput.files[0];
        if (file) {
            const fileExtension = '.' + file.name.split('.').pop();
            if (allowedExtensions.includes(fileExtension)) {
                errorMessage.textContent = '불가한 확장자명입니다.';
            } else {
                errorMessage.textContent = '가능한 확장자명입니다.';
            }
        } else {
            errorMessage.textContent = '파일을 선택해주세요.';
        }
    });

    fileInput.addEventListener('change', function() {
        const file = fileInput.files[0];
        if (file) {
            const fileExtension = '.' + file.name.split('.').pop();
            errorMessage.textContent = `선택된 파일: ${file.name} (확장자: ${fileExtension})`;
        }
    });
});





