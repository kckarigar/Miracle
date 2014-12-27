var __FORM_INFO__ = [
  {
    id: 'name',
    title: "Customer Full Name",
    glyph: "user",
    type: "text",
    inputGrCl: "input-group input-group-lg",
    min: 3,
    max: 50,
    regex: null
  },
  {
    id: 'email',
    title: "Email Adrress",
    glyph: "envelope",
    type: "email",
    inputGrCl: "input-group",
    min: 6,
    max: 25,
    regex: /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  },
  {
    id: 'telephone',
    title: "Phone number",
    glyph: "phone",
    type: "text",
    inputGrCl: "input-group",
    min: 7,
    max: 50,
    regex: /^\+?(\d| |-)*$/
  },
  {
    id: 'address_id',
    title: "Address",
    glyph: "",
    type: "hidden",
    inputGrCl: "",
    regex: null
  },
  {
    id: 'street',
    title: "Street",
    glyph: "home",
    type: "text",
    inputGrCl: "input-group",
    min: 0,
    max: 100,
    regex: null
  },
  {
    id: 'city',
    title: "City",
    glyph: "home",
    type: "text",
    inputGrCl: "input-group",
    min: 0,
    max: 50,
    regex: null
  },
  {
    id: 'state',
    title: "State",
    glyph: "home",
    type: "text",
    inputGrCl: "input-group",
    min: 0,
    max: 25,
    regex: null
  },
  {
    id: 'zip',
    title: "zip",
    glyph: "home",
    type: "text",
    inputGrCl: "input-group",
    min: 5,
    max: 7,
    regex: null
  }
];

function getGlyph(glyphName) {
  return 'glyphicon glyphicon-' + glyphName;
}

var CustomerRow = React.createClass({
  handleDelete: function () {
    if (this.props.isEditable) {
      this.props.delete(this.props.data.id);
    }
  },
  handleEdit: function () {
    if (this.props.isEditable) {
      this.props.edit(this.props.data.id);
    }
  },
  render: function () {
    var cursor = this.props.isEditable ? 'pointer' : 'default';
    return (
      <div className="list-group-item">
        <h3 className="list-group-item-heading">
    	          
          {this.props.data.name}
          <span
            className={getGlyph('pencil')}
            onClick={this.handleEdit}
            style={{color: this.props.isEditable ? '#f0ad4e' : 'grey', cursor: cursor}}
            title="Edit"
          />
        </h3>

        <ol className="breadcrumb">
          <li  title = "Email">
            <span className={getGlyph('envelope')} />
            {this.props.data.email}
          </li>
          <li  title = "Telephone">
            <span className={getGlyph('phone')} />
            {this.props.data.telephone}
          </li>
          <li title = "Street">
            <span className={getGlyph('home')} title = "Address"/> {this.props.data.address.street}
          </li>
          <li title = "City">
            {this.props.data.address.city}
          </li>
          <li title = "State">
            {this.props.data.address.state}
          </li>
          <li title = "Zip">
            {this.props.data.address.zip}
          </li>
          <li>
            <span
              className={getGlyph('remove')}
              onClick={this.handleDelete}
              style={{color: this.props.isEditable ? '#d9534f' : 'grey', cursor: cursor}}
              title="Delete"
            />
          </li>
        </ol>

      </div>
    );
  }
});

var EditCustomerRow = React.createClass({
  handleCancel: function () {
    this.props.cancel();
  },
  getInitialState: function () {
    return {errors: {}}
  },
  isValid: function () {
    var errors = {};
    __FORM_INFO__.forEach(function (info) {
      if (!info.id in Object.keys(this.refs)) {
        return;
      }
      var value = this.refs[info.id].getDOMNode().value.trim();

      if (value.length < info.min || value.length > info.max) {
        errors[info.id] = 'The ' + info.title.toLowerCase() + ' field is too ' + (value.length < info.min ? 'short!' : 'long!');
      } else if (info.regex && !info.regex.test(value)) {
        errors[info.id] = 'The ' + info.title.toLowerCase() + ' seems to be invalid!';
      }
    }.bind(this));
    this.setState({errors: errors});
    return Object.keys(errors).length === 0;
  },
  handleSave: function () {
    if (!this.isValid()) {
      return;
    }

    this.props.save({
      id: this.props.data.id,
      name: this.refs.name.getDOMNode().value.trim(),
      email: this.refs.email.getDOMNode().value.trim(),
      telephone: this.refs.telephone.getDOMNode().value.trim(),
      address: {
        address_id: this.refs.address_id.getDOMNode().value.trim(),
        street: this.refs.street.getDOMNode().value.trim(),
        city: this.refs.city.getDOMNode().value.trim(),
        state: this.refs.state.getDOMNode().value.trim(),
        zip: this.refs.zip.getDOMNode().value.trim()
      }
    });
  },
  renderField: function (info, defaultValue) {
    if (info.type === 'hidden') {
      return this.renderFieldHidden(info, defaultValue);
    }
    var classes = React.addons.classSet({
      'form-group': true,
      'has-error': info.id in this.state.errors
    });
    return (
      <div className={classes}>
        <div className={info.inputGrCl}>
          <div className="input-group-addon">
            <span className={getGlyph(info.glyph)} title={info.title} />
          </div>
          <input type={info.type} className="form-control" defaultValue={defaultValue} placeholder={info.title} name={info.id} id={info.id} ref={info.id} />
        </div>
        <div>
          <span>{this.state.errors[info.id]}</span>
        </div>
      </div>
    );
  },
  renderFieldHidden: function (info, defaultValue) {
    return (
      <div className="form-group">
        <input type={info.type} className="form-control" defaultValue={defaultValue} placeholder={info.title} name={info.id} id={info.id} ref={info.id} />
        <span>
          <h3>{info.title}:</h3>
        </span>
      </div>
    );
  },
  renderSaveCancel: function () {
    return <div className="form-group">
      <h2>
        <span
          className={getGlyph('ok-circle')}
          onClick={this.handleSave}
          style={{color: '#5cb85c', cursor: 'pointer'}}
          title="Save"
        />
        <span
          className={getGlyph('ban-circle')}
          onClick={this.handleCancel}
          style={{color: '#d3d3d3', cursor: 'pointer'}}
          title="Cancel"
        />
      </h2>
    </div>
  },
  render: function () {
    var fields = [];
    __FORM_INFO__.forEach(function(info) {
      fields.push(this.renderField(info, info.id in this.props.data ? this.props.data[info.id] : this.props.data.address[info.id]));
    }.bind(this));

    return (
      <div className="list-group-item">
        <div className="container" >
          <div className="row">
            <div className="col-sm-3 col-md-6 col-lg-4">
              <form  className="form-horizontal" role="form" id="customerForm">
                {this.renderSaveCancel()}
                {fields}
		     	      {this.renderSaveCancel()}
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
});

var CustomerTable = React.createClass({
  componentDidMount: function () {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      success: function (data) {
        this.setState({data: data, editId: -1});
      }.bind(this)
    });
  },
  getInitialState: function () {
    return {data: [], editId: -1};
  },
  setEditId: function (id) {
    this.setState({data: this.state.data, editId: id});
  },
  add: function () {
    this.setEditId(0);
  },
  cancel: function () {
    this.setEditId(-1);
  },
  delete: function (id) {
    $.ajax({
      url: this.props.url + id,
      dataType: 'json',
      type: 'DELETE',
      success: function (data) {
        this.setState({data: data, editId: -1});
      }.bind(this)
    });

    var data = [];
    for (var i = 0; i < this.state.data.length; ++i) {
      if (this.state.data[i].id !== id) {
        data.push(this.state.data[i]);
      }
    }
    this.setState({data: data, editId: -1});
  },
  edit: function (id) {
    this.setEditId(id);
  },
  save: function (userData) {
    this.setEditId(-1);
    var type = 'PUT';
    if (userData.id === 0) {
      type = 'POST';
    }

    $.ajax({
      url: this.props.url,
      contentType: "application/json",
      data: JSON.stringify(userData),
      dataType: 'json',
      type: type,
      success: function (data) {
        this.setState({data: data, editId: -1});
      }.bind(this)
    });
  },
  getEditCustomerRow: function (customerData) {
    return (
      <EditCustomerRow
        cancel={this.cancel}
        data={customerData}
        key={customerData.id}
        save={this.save}
      />
    );
  },
  render: function () {
    var addButton = this.state.editId === -1 ?
      <span
        className={getGlyph('plus')}
        onClick={this.add} style={{
          color: '#5cb85c',
          cursor: 'pointer'
        }}
        title="Add"
      /> :
      <span className={getGlyph('plus')}style={{color: 'gray', cursor: 'default'}}  />;

    var newCustomerRow = null;
    if (this.state.editId === 0) {
      newCustomerRow = this.getEditCustomerRow({id: 0, address: {}});
    }

    var rows = this.state.data.map(function (customerData) {
      if (this.state.editId === customerData.id) {
        return this.getEditCustomerRow(customerData);
      }
      return (
        <CustomerRow
          data={customerData}
          delete={this.delete}
          edit={this.edit}
          isEditable={this.state.editId === -1}
          key={customerData.id}
        />
      );
    }.bind(this));
    return (
      <div className="container">
        <h1>
          Customer List {addButton}
        </h1>
        <div className="list-group">
          {newCustomerRow}
          {rows}
        </div>
      </div>
    );
  }
});

React.render(
  <CustomerTable url="customers/" />,
  document.getElementById('main')
);
