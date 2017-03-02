var IntlMixin = ReactIntl.IntlMixin;
var FormattedRelative = ReactIntl.FormattedRelative;

var intlData = {
    "locales": "nb-NO"
};

var Entry = React.createClass({displayName: "Entry",
  mixins: [IntlMixin],
  render: function() {
    var imageUrl = 'url('+this.props.image+')';
    var timeReleased = parseInt(this.props.released);
    var entryLink = "#/entry/"+this.props.id;
    var entryOnClick = "window.location.href='"+entryLink+"';";

    return (
      React.createElement("div", {key: this.props.id, className: "mdl-card mdl-cell mdl-cell--12-col"}, 
        React.createElement("div", {className: "mdl-card__media mdl-color-text--grey-50", style: {backgroundImage: imageUrl}}, 
          React.createElement("h3", null, React.createElement("a", {href: entryLink}, this.props.title))
        ), 
        React.createElement("div", {className: "mdl-card__supporting-text meta mdl-color-text--grey-600"}, 
          React.createElement("div", {className: "minilogo"}), 
          React.createElement("div", null, 
            React.createElement("strong", null, "ekun"), 
            React.createElement("span", null, React.createElement(FormattedRelative, {value: timeReleased}))
          )
        )
      )
    );
  }
});

var EntryList = React.createClass({displayName: "EntryList",
  render: function() {
    var newsNode = this.props.data.map(function (newsItem) {
      return (
        React.createElement(Entry, React.__spread({key: newsItem.id, title: newsItem.title, id: newsItem.id, released: newsItem.released, image: newsItem.image},  intlData))
      );
    });
    return (
      React.createElement("div", {className: "demo-blog__posts mdl-grid"}, 
        newsNode
      )
    );
  }
});

var EntryListBox = React.createClass({displayName: "EntryListBox",
  getInitialState: function() {
    return {data: {news: []}};
  },
  componentDidMount: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  componentDidUpdate: function(prevProps, prevState) {
    cardsAsLinks();
  },
  render: function() {
    return (
      React.createElement(EntryList, {data: this.state.data.news})
    );
  }
});
var PromotedEntry = React.createClass({displayName: "PromotedEntry",
  mixins: [IntlMixin],
  getInitialState: function() {
    return {data: {id:"-1",released:"1420506000000",title:"",image:""}};
  },
  componentDidMount: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data.first});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  render: function() {
    var imageUrl = 'url('+this.state.data.image+')';
    var timeReleased = parseInt(this.state.data.released);
    var entryLink = "#/entry/"+this.state.data.id;

    return (
      React.createElement("div", {className: "mdl-card mdl-cell mdl-cell--8-col"}, 
        React.createElement("div", {className: "mdl-card__media mdl-color-text--grey-50", style: {backgroundImage: imageUrl}}, 
          React.createElement("h3", null, React.createElement("a", {href: entryLink}, this.state.data.title))
        ), 
        React.createElement("div", {className: "mdl-card__supporting-text meta mdl-color-text--grey-600"}, 
          React.createElement("div", {className: "minilogo"}), 
          React.createElement("div", null, 
            React.createElement("strong", null, "ekun"), 
            React.createElement("span", null, React.createElement(FormattedRelative, {value: timeReleased}))
          )
        )
      )
    );
  }
});

var Home = React.createClass({displayName: "Home",
  render: function() {
    var promtedEntryUrl = window.location.protocol + "//api.glittum.org/news/?action=setup";
    var entryListBoxUrl = window.location.protocol + "//api.glittum.org/news/?action=list";
    return (
      React.createElement("div", {className: "demo-blog__posts mdl-grid"}, 
        React.createElement(PromotedEntry, React.__spread({url: promtedEntryUrl},  intlData)), 
        React.createElement("div", {className: "mdl-card something-else mdl-cell mdl-cell--8-col mdl-cell--4-col-desktop"}, 
          React.createElement("div", {className: "mdl-card__media mdl-color--white mdl-color-text--grey-600"}, 
            React.createElement("img", {src: "images/logo.png"})
          ), 
          React.createElement("div", {className: "head-box meta meta--fill mdl-color-text--grey-600"}, 
            React.createElement("div", null, 
              React.createElement("a", {href: "#/about"}, React.createElement("strong", null, "Marius Bækken Glittum"))
            )
          )
        ), 
        React.createElement(EntryListBox, {url: entryListBoxUrl})
      )
    );
  }
});

var About = React.createClass({displayName: "About",
  render: function() {
    showElementWithId("#back-to-main-btn");
    return (
      React.createElement("div", {className: "demo-blog__posts mdl-grid"}, 
        React.createElement("div", {className: "mdl-card mdl-cell mdl-cell--8-col"}, 
          React.createElement("div", {className: "mdl-card__supporting-text meta mdl-color-text--grey-600"}, 
            React.createElement("p", null, "To be filled!")
          )
        ), 
        React.createElement("div", {className: "mdl-card something-else mdl-cell mdl-cell--8-col mdl-cell--4-col-desktop"}, 
          React.createElement("div", {className: "mdl-card__media mdl-color--white mdl-color-text--grey-600"}, 
            React.createElement("img", {src: "images/logo.png"})
          ), 
          React.createElement("div", {className: "head-box meta meta--fill mdl-color-text--grey-600"}, 
            React.createElement("div", null, 
              React.createElement("a", {href: "https://www.linkedin.com/in/mariusglittum", target: "_BLANK"}, React.createElement("strong", null, "Marius Bækken Glittum @ LinkedIn"))
            )
          )
        ), 
        React.createElement("div", {className: "mdl-card something-else mdl-cell mdl-cell--8-col mdl-cell--4-col-desktop"}, 
          React.createElement("div", {className: "mdl-card__media mdl-color--white mdl-color-text--grey-600"}, 
            React.createElement("img", {src: "images/logo.png"})
          ), 
          React.createElement("div", {className: "head-box meta meta--fill mdl-color-text--grey-600"}, 
            React.createElement("div", null, 
              React.createElement("a", {href: "https://www.linkedin.com/in/mariusglittum", target: "_BLANK"}, React.createElement("strong", null, "Marius Bækken Glittum @ GitHub"))
            )
          )
        ), 
        React.createElement("div", {className: "mdl-card something-else mdl-cell mdl-cell--8-col mdl-cell--4-col-desktop"}, 
          React.createElement("div", {className: "mdl-card__media mdl-color--white mdl-color-text--grey-600"}, 
            React.createElement("img", {src: "images/logo.png"})
          ), 
          React.createElement("div", {className: "head-box meta meta--fill mdl-color-text--grey-600"}, 
            React.createElement("div", null, 
              React.createElement("a", {href: "https://www.linkedin.com/in/mariusglittum", target: "_BLANK"}, React.createElement("strong", null, "Marius Bækken Glittum @ GitHub"))
            )
          )
        ), 
        React.createElement("div", {className: "mdl-card something-else mdl-cell mdl-cell--8-col mdl-cell--4-col-desktop"}, 
          React.createElement("div", {className: "mdl-card__media mdl-color--white mdl-color-text--grey-600"}, 
            React.createElement("img", {src: "images/logo.png"})
          ), 
          React.createElement("div", {className: "head-box meta meta--fill mdl-color-text--grey-600"}, 
            React.createElement("div", null, 
              React.createElement("a", {href: "https://www.linkedin.com/in/mariusglittum", target: "_BLANK"}, React.createElement("strong", null, "Marius Bækken Glittum @ GitHub"))
            )
          )
        )
      )
    );
  }
});

var DetailedEntryView = React.createClass({displayName: "DetailedEntryView",
  mixins: [IntlMixin],
  fetchEntry: function(entryApiUrl) {
    $.ajax({
      url: entryApiUrl,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(entryApiUrl, status, err.toString());
      }.bind(this)
    });
  },
  getInitialState: function() {
    return {data: {id:"-1",released:"1420506000000",title:"Loading",image:"images\/default\/slide-07.jpg",text:"",next:{id:"-1",title:"Dummy!",text:""},previous:{id:"-1",title:"Dummy!",text:""}}};
  },
  componentDidMount: function() {
    this.fetchEntry(this.props.url);
  },
  componentWillReceiveProps: function(nextProps) {
    this.fetchEntry(nextProps.url);
  },
  shouldComponentUpdate: function(nextProps, nextState) {
    return nextState.data.id !== this.state.data.id;
  },
  render: function() {
    var imageUrl = 'url('+this.state.data.image+')';
    var timeReleased = parseInt(this.state.data.released);
    var rawMarkup = marked(this.state.data.text, {sanitize: false});

    var nextItemDisabled = !Boolean(this.state.data.next.id);
    var nextItemLink = "#/entry/"+this.state.data.next.id;
    var previousItemDisabled = !Boolean(this.state.data.previous.id);
    var previousItemLink = "#/entry/"+this.state.data.previous.id;

    return (
        React.createElement("div", {className: "demo-blog__posts mdl-grid"}, 
          React.createElement("div", {id: "entry", className: "mdl-card mdl-shadow--4dp mdl-cell mdl-cell--12-col"}, 
            React.createElement("div", {className: "mdl-card__media mdl-color-text--grey-50", style: {backgroundImage: imageUrl, height: '280px'}}, 
              React.createElement("h3", null, this.state.data.title)
            ), 
            React.createElement("div", {className: "mdl-color-text--grey-700 mdl-card__supporting-text meta"}, 
              React.createElement("div", {className: "minilogo"}), 
              React.createElement("div", null, 
                React.createElement("strong", null, "ekun"), 
                React.createElement("span", null, React.createElement(FormattedRelative, {value: timeReleased}))
              ), 
              React.createElement("div", {className: "section-spacer"})
            ), 
            React.createElement("div", {className: "mdl-color-text--grey-700 mdl-card__supporting-text entry-paragraph", dangerouslySetInnerHTML: {__html: rawMarkup}}
            )
          ), 

          React.createElement("nav", {className: "demo-nav mdl-color-text--grey-50 mdl-cell mdl-cell--12-col"}, 
            React.createElement(BottomNavButton, {icon: "arrow_back", text: "Newer", link: nextItemLink, disabled: nextItemDisabled}), 
            React.createElement("div", {className: "section-spacer"}), 
            React.createElement(BottomNavButton, {icon: "arrow_forward", text: "Older", link: previousItemLink, disabled: previousItemDisabled})
          ), 
          React.createElement("div", {className: "mdl-tooltip", htmlFor: "arrow_back"}, this.state.data.next.title), 
          React.createElement("div", {className: "mdl-tooltip", htmlFor: "arrow_forward"}, this.state.data.previous.title)
        )
    );
  }
});

var Loading = React.createClass({displayName: "Loading",
  componentDidUpdate: function(prevProps, prevState) {
    componentHandler.upgradeAllRegistered();
  },
  render: function() {
    return (
      React.createElement("div", {id: "loading", className: "loading-entry mdl-card mdl-shadow--4dp mdl-cell mdl-cell--12-col"}, 
          React.createElement("div", {class: "mdl-spinner mdl-js-spinner is-active"})
      )
    );
  }
});

var LoadingData = React.createClass({displayName: "LoadingData",
  shouldComponentUpdate: function(nextProps, nextState) {
    return nextProps.disabled !== this.props.disabled;
  },
  render: function() {
    var Child;
    if(this.props.disabled) {
      Child = Blank;
    } else {
      Child = Loading;
    }
    return (
      React.createElement(Child, null)
    );
  }
});

var BottomNavButton = React.createClass({displayName: "BottomNavButton",
  render: function() {
    var Child;
    if(this.props.disabled) {
      Child = Blank;
    } else {
      Child = EnabledNavButton;
    }
    return (
      React.createElement(Child, {icon: this.props.icon, text: this.props.text, link: this.props.link})
    );
  }
});

var EnabledNavButton = React.createClass({displayName: "EnabledNavButton",
  render: function() {
    var olderText;
    var newerText;
    if(this.props.icon === "arrow_forward") {
      olderText = this.props.text;
    } else {
      newerText = this.props.text;
    }
    return (
      React.createElement("a", {href: this.props.link, className: "demo-nav__button"}, 
        olderText, 
        React.createElement("button", {className: "mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon mdl-color--white mdl-color-text--grey-900"}, 
          React.createElement("i", {className: "material-icons"}, this.props.icon)
        ), 
        newerText
      )
    );
  }
});

var Blank = React.createClass({displayName: "Blank",
  render: function() {
    return false;
  }
});

var DisplayEntry = React.createClass({displayName: "DisplayEntry",
  render: function() {
    var requestedId = this.props.route[1];
    var entryApiUrl = window.location.protocol + "//api.glittum.org/news/?action=get&id=" + requestedId;
    showElementWithId("#back-to-main-btn");

    return (
      React.createElement(DetailedEntryView, React.__spread({url: entryApiUrl},  intlData))
    );
  }
});

var App = React.createClass({displayName: "App",
  componentDidUpdate: function(prevProps, prevState) {
    componentHandler.upgradeAllRegistered();
  },
  render: function() {
    hideElementWithId("#back-to-main-btn");
    var DefaultRoute = Home;
    var Child;
    var routeInfo = this.props.route.substring(1).split("/");

    switch (routeInfo[0]) {
      case 'about': Child = About; break;
      case 'entry': Child = DisplayEntry; break;
      case 'page': Child = Home; break;
      default:      Child = DefaultRoute;
    }

    return (
      React.createElement(Child, {route: routeInfo})
    )
  }
});

function render () {
  var route = window.location.hash.substr(1);
  React.render(React.createElement(App, {route: route}), document.getElementById('content'));
}

window.addEventListener('hashchange', render);
render(); // render initially