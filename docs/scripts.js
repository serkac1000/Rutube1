document.addEventListener('DOMContentLoaded', function() {
    // Tab functionality
    const tabs = document.querySelectorAll('.tab');
    const tabContents = document.querySelectorAll('.tab-content');
    
    if (tabs.length > 0 && tabContents.length > 0) {
        tabs.forEach(tab => {
            tab.addEventListener('click', () => {
                // Remove active class from all tabs and contents
                tabs.forEach(t => t.classList.remove('active'));
                tabContents.forEach(content => content.classList.remove('active'));
                
                // Add active class to current tab and content
                tab.classList.add('active');
                const target = tab.getAttribute('data-target');
                document.getElementById(target).classList.add('active');
            });
        });
    }
    
    // Collapsible sections
    const collapsibles = document.querySelectorAll('.collapsible-header');
    
    if (collapsibles.length > 0) {
        collapsibles.forEach(header => {
            header.addEventListener('click', () => {
                header.classList.toggle('active');
                const content = header.nextElementSibling;
                if (content.style.maxHeight) {
                    content.style.maxHeight = null;
                } else {
                    content.style.maxHeight = content.scrollHeight + "px";
                }
            });
        });
    }
    
    // Highlight current page in navigation
    const currentPage = window.location.href.split('/').pop();
    const navLinks = document.querySelectorAll('nav a');
    
    navLinks.forEach(link => {
        const linkPage = link.href.split('/').pop();
        if (currentPage === linkPage) {
            link.classList.add('active');
        } else if (currentPage === '' && linkPage === 'index.html') {
            link.classList.add('active');
        }
    });
    
    // Show/hide code examples
    const codeToggleButtons = document.querySelectorAll('.toggle-code');
    
    if (codeToggleButtons.length > 0) {
        codeToggleButtons.forEach(button => {
            button.addEventListener('click', () => {
                const codeBlock = document.getElementById(button.getAttribute('data-target'));
                if (codeBlock.style.display === 'none' || codeBlock.style.display === '') {
                    codeBlock.style.display = 'block';
                    button.textContent = 'Hide Code';
                } else {
                    codeBlock.style.display = 'none';
                    button.textContent = 'Show Code';
                }
            });
        });
    }
    
    // Copy code to clipboard functionality
    const copyButtons = document.querySelectorAll('.copy-code');
    
    if (copyButtons.length > 0) {
        copyButtons.forEach(button => {
            button.addEventListener('click', () => {
                const codeBlock = document.getElementById(button.getAttribute('data-target'));
                const textArea = document.createElement('textarea');
                textArea.value = codeBlock.textContent;
                document.body.appendChild(textArea);
                textArea.select();
                document.execCommand('copy');
                document.body.removeChild(textArea);
                
                // Show copied message
                const originalText = button.textContent;
                button.textContent = 'Copied!';
                setTimeout(() => {
                    button.textContent = originalText;
                }, 2000);
            });
        });
    }
});
