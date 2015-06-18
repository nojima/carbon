import React = require('react');
import Editor = require('../components/Editor');
import { Action } from '../actions/editor/Actions';
import { ActionCreator } from '../actions/editor/ActionCreator';
import { Store } from '../stores/editor/Store';
import { Dispatcher } from '../dispatchers/editor/Dispatcher';

var dispatcher: Dispatcher = new Dispatcher();

var actionCreator: ActionCreator = new ActionCreator(dispatcher);

var store: Store = new Store(dispatcher);

var editorEl: any = React.createElement(Editor.Component, {
    'actionCreator': actionCreator, 'store': store
});

var editorWrapperEl: any = document.getElementById('carbon-editor');
if (editorWrapperEl) {
    React.render(editorEl, editorWrapperEl);
}

