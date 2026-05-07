document.addEventListener('DOMContentLoaded', function() {
    // Delete Confirmation
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to delete this event?')) {
                e.preventDefault();
            }
        });
    });
});