function cardsAsLinks() {
  Array.prototype.forEach.call(document.querySelectorAll('.mdl-card__media'), function(el) {
    var link = el.querySelector('a');
    if(!link) {
      return;
    }
    var target = link.getAttribute('href');
    if(!target) {
      return;
    }
    el.removeEventListener('click', function() {
      window.location.href = target;
    });
    el.addEventListener('click', function() {
      window.location.href = target;
    });
  });
}

function hideElementWithId(elementId) {
  var element = $(elementId);
  if(element) {
    $(elementId).hide();
  }
}

function showElementWithId(elementId) {
  var element = $(elementId);
  if(element) {
    element.show();
  }
}
