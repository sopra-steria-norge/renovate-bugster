// bullshit
var IntlMixin = ReactIntl.IntlMixin;
var FormattedRelative = ReactIntl.FormattedRelative;

var intlData = {
    "locales": "nb-NO"
};

var Entry = React.createClass({
    mixins: [IntlMixin],
    render: function () {
        if (this.props.item.status === "inprogress") {
            return (
                <div key={this.props.id} className="mdl-card mdl-cell mdl-cell--12-col">
                    <div className="mdl-card__media mdl-color-text--grey-50" style={{backgroundColor: "lightgreen"}}>
                        <h3 color="black"><a href={`#/bet/${this.props.id}`}>{this.props.item.homeTeam.name} <b>{this.props.item.score.home}</b> vs <b>{this.props.item.score.away}</b> {this.props.item.awayTeam.name}</a></h3>
                    </div>
                    <div className="mdl-card__supporting-text meta mdl-color-text--grey-600">
                        <div>
                            <span>Startet <FormattedRelative value={this.props.item.startDate}/></span>
                        </div>
                    </div>
                </div>
            );
        } else if (this.props.item.status === "notstarted") {
            return (
                <div key={this.props.id} className="mdl-card mdl-cell mdl-cell--12-col">
                    <div className="mdl-card__media mdl-color-text--grey-50" style={{backgroundColor: "lightcoral"}}>
                        <h3><a href={`#/bet/${this.props.id}`}>{this.props.item.homeTeam.name} vs {this.props.item.awayTeam.name}</a></h3>
                    </div>
                    <div className="mdl-card__supporting-text meta mdl-color-text--grey-600">
                        <div>
                            <span>Starter <FormattedRelative value={this.props.item.startDate}/></span>
                        </div>
                    </div>
                </div>
            );
        } else {
            return (
                <div key={this.props.id} className="mdl-card mdl-cell mdl-cell--12-col">
                    <div className="mdl-card__media mdl-color-text--grey-50" style={{backgroundColor: "darkgrey"}}>
                        <h3 color="black"><a href={`#/bet/${this.props.id}`}>{this.props.item.homeTeam.name} <b>{this.props.item.score.home}</b> vs <b>{this.props.item.score.away}</b> {this.props.item.awayTeam.name}</a></h3>
                    </div>
                    <div className="mdl-card__supporting-text meta mdl-color-text--grey-600">
                        <div>
                            <span>Ferdigspilt med start <FormattedRelative value={this.props.item.startDate}/></span>
                        </div>
                    </div>
                </div>
            );
        }
    }
});

var EntryList = React.createClass({
    render: function () {
        var newsNode = this.props.data.map(function (newsItem) {
            return (
                <Entry key={newsItem.id} item={newsItem} id={newsItem.id} {...intlData} />
            );
        });
        return (
            <div className="demo-blog__posts mdl-grid">
                {newsNode}
            </div>
        );
    }
});

var EntryListBox = React.createClass({
    getInitialState: function () {
        return {data: {matches: []}};
    },
    componentDidMount: function () {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({data: {matches: data}});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    componentDidUpdate: function (prevProps, prevState) {
        cardsAsLinks();
    },
    render: function () {
        return (
            <EntryList data={this.state.data.matches}/>
        );
    }
});

var Home = React.createClass({
    render: function () {
        const entryListBoxUrl = "http://localhost:8000/bugster/api/matches";
        return (
            <div className="demo-blog__posts mdl-grid">
                <EntryListBox url={entryListBoxUrl}/>
            </div>
        );
    }
});

var DetailedEntryView = React.createClass({
    mixins: [IntlMixin],
    fetchEntry: function (entryApiUrl) {
        $.ajax({
            url: entryApiUrl,
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({data: data});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(entryApiUrl, status, err.toString());
            }.bind(this)
        });
    },
    getInitialState: function () {
        return {
            data: {
                "id": 1,
                "homeTeam": {
                    "name": "Real Betis"
                },
                "awayTeam": {
                    "name": "Real Sociedad"
                },
                "score": {
                    "id": 1,
                    "home": 1,
                    "away": 2
                },
                "status": "inprogress",
                "startDate": 1488566700000,
                "odds": [
                    {
                        "id": 673,
                        "value": 1.12,
                        "matchId": 1,
                        "result": "H"
                    },
                    {
                        "id": 674,
                        "value": 1.83,
                        "matchId": 1,
                        "result": "B"
                    },
                    {
                        "id": 675,
                        "value": 1.74,
                        "matchId": 1,
                        "result": "U"
                    }
                ]
            }
        }
    },
    componentDidMount: function () {
        this.fetchEntry(this.props.url);
    },
    componentWillReceiveProps: function (nextProps) {
        this.fetchEntry(nextProps.url);
    },
    shouldComponentUpdate: function (nextProps, nextState) {
        return nextState.data.homeTeam.name !== this.state.data.homeTeam.name || nextState.data.awayTeam.name !== this.state.awayTeam.name;
    },    render: function () {
        var oddsNodes = this.state.data.odds.map(function (odd) {
            return (
                <div className="mdl-card mdl-cell mdl-cell--4-col">
                    <div className="mdl-card__media mdl-color-text--grey-600 mdl-color--primary">
                        <center><strong><a href={`#/confirm/${odd.id}/${odd.result}`}>{odd.result}</a></strong></center>
                    </div>
                    <div className="head-box meta meta--fill mdl-color-text--grey-600">
                        <div>
                            <strong>{odd.value}</strong>
                        </div>
                    </div>
                </div>
            );
        });
        if (this.state.data.status === "inprogress") {
            return (
                <div className="demo-blog__posts mdl-grid">
                    <div key={this.state.data.id} className="mdl-card mdl-cell mdl-cell--12-col">
                        <div className="mdl-card__media mdl-color-text--grey-50"
                             style={{backgroundColor: "lightgreen"}}>
                            <h3 color="black">{this.state.data.homeTeam.name} <b>{this.state.data.score.home}</b> vs
                                <b>{this.state.data.score.away}</b> {this.state.data.awayTeam.name}</h3>
                        </div>
                        <div className="mdl-card__supporting-text meta mdl-color-text--grey-600">
                            <div>
                                <span>Startet <FormattedRelative value={this.state.data.startDate}/></span>
                            </div>
                        </div>
                    </div>
                    {oddsNodes}
                </div>
            );
        } else if(this.state.data.status === "notstarted") {
            return (
                <div className="demo-blog__posts mdl-grid">
                    <div key={this.state.data.id} className="mdl-card mdl-cell mdl-cell--12-col">
                        <div className="mdl-card__media mdl-color-text--grey-50"
                             style={{backgroundColor: "lightcoral"}}>
                            <h3>{this.state.data.homeTeam.name} vs {this.state.data.awayTeam.name}</h3>
                        </div>
                        <div className="mdl-card__supporting-text meta mdl-color-text--grey-600">
                            <div>
                                <span>Starter <FormattedRelative value={this.state.data.startDate}/></span>
                            </div>
                        </div>
                    </div>
                    {oddsNodes}
                </div>
            );
        } else {
            return (
                <div key={this.state.data.id} className="mdl-card mdl-cell mdl-cell--12-col">
                    <div className="mdl-card__media mdl-color-text--grey-50" style={{backgroundColor: "darkgrey"}}>
                        <h3>{this.state.data.homeTeam.name} <b>{this.state.data.score.home}</b> vs <b>{this.state.data.score.away}</b> {this.state.data.awayTeam.name}</h3>
                    </div>
                    <div className="mdl-card__supporting-text meta mdl-color-text--grey-600">
                        <div>
                            <span>Ferdigspilt med start <FormattedRelative value={this.state.data.startDate}/></span>
                        </div>
                    </div>
                </div>
            );
        }
    }
});

var ConfirmBet = React.createClass({
    mixins: [IntlMixin],
    handleInputChange: function (event) {
        const target = event.target;
        const name = target.name;
        const value = target.value;
        this.setState({
            [name]: value
        });
    },
    handleSubmit: function (event) {
        event.preventDefault();
        var entryApiUrl = "http://localhost:8000/bugster/api/bets";
        $.ajax({
            url: entryApiUrl,
            dataType: 'json',
            cache: false,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(this.state),
            success: function (data) {
                alert("Bet plassert.");
                window.location.replace("http://localhost:8000/bugster/")
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(entryApiUrl, status, err.toString());
            }.bind(this)
        });
    },
    getInitialState: function () {
        return {
            amount: 100,
            odds: {
                id: this.props.route[1]
            },
            user : this.props.user
        };
    },
    render: function () {
        return (
            <div className="demo-blog__posts mdl-grid">
                <div className="mdl-card mdl-cell mdl-cell--12-col">
                    <div className="mdl-card__media mdl-color-text--grey-600 mdl-color--primary">
                        <center><strong>Plasser bet</strong></center>
                    </div>
                    <div className="head-box meta meta--fill mdl-color-text--grey-600">
                        <div>
                    <form onSubmit={this.handleSubmit}>
                        <input name="amount" type="number"
                               value={this.state.amount}
                               onChange={this.handleInputChange} />
                        <button type="submit">Bekreft</button>
                    </form>
                        </div>
                    </div>
                    </div>
            </div>
        );
    }
});

var DisplayEntry = React.createClass({
    render: function () {
        var requestedId = this.props.route[1];
        var entryApiUrl = "http://localhost:8000/bugster/api/matches/" + requestedId;
        showElementWithId("#back-to-main-btn");

        return (
            <DetailedEntryView url={entryApiUrl} {...intlData} />
        );
    }
});

var App = React.createClass({
    componentDidUpdate: function (prevProps, prevState) {
        componentHandler.upgradeAllRegistered();
    },
    fetchEntry: function () {
        $.ajax({
            url: "http://localhost:8000/bugster/api/users",
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({data: {user: data}});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error("http://localhost:8000/bugster/api/users", status, err.toString());
            }.bind(this)
        });
    },
    componentDidMount: function () {
        this.fetchEntry();
    },
    shouldComponentUpdate: function (nextProps, nextState) {
        return this.props.route !== nextProps.route || this.state.data.user.id !== nextState.data.user.id;
    },
    getInitialState: function () {
        return {
            data: { user: {id: 0, username: "NA", balance: 0}}
        };
    },
    render: function () {
        hideElementWithId("#back-to-main-btn");
        const DefaultRoute = Home;
        let Child;
        const routeInfo = this.props.route.substring(1).split("/");

        switch (routeInfo[0]) {
            case 'page':
                Child = Home;
                break;
            case 'bet':
                Child = DisplayEntry;
                break;
            case 'confirm':
                Child = ConfirmBet;
                break;
            default:
                Child = DefaultRoute;
        }
        let userInfoString = this.state.data.user.username +" (" + this.state.data.user.balance + " $)";
        $("#userInfo").text(userInfoString);
        return (
            <Child route={routeInfo} user={this.state.data.user}/>
        )
    }
});

function render() {
    var route = window.location.hash.substr(1);
    React.render(<App route={route}/>, document.getElementById('content'));
}

window.addEventListener('hashchange', render);
render(); // render initially
