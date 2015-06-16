import React = require('react');
import Input = require('./editor/Input');
import Preview = require('./editor/Preview');
import { ViewType } from './editor/ViewType';
import { ActionCreator } from '../actions/editor/ActionCreator';
import { Store } from '../stores/editor/Store';
import { StoreEventType } from '../stores/StoreEventType';

export interface Props {
    actionCreator: ActionCreator;
    store: Store;
}


interface State {
    currentViewType?: ViewType;
    html?: string;
    text?: string;
}


export class Component extends React.Component<Props, State> {
    constructor(props: Props, context: any) {
        super(props, context);
        this.state = {
            'currentViewType': ViewType.INPUT,
            'text': '',
            'html': ''
        };
    }

    componentDidMount(): void {
        this.props.store.on(
            StoreEventType.CHANGE, this.handleChange, this);
    }

    componentWillUnmount(): void {
        this.props.store.off(
            StoreEventType.CHANGE, this.handleChange);
    }

    render(): React.ReactElement<any> {
        var inputButtonEl: any = React.createElement('button', {
            'onClick': this.handleChangeView.bind(this, ViewType.INPUT)
        }, '編集');

        var previewButtonEl: any = React.createElement('button', {
            'onClick': this.handleChangeView.bind(this, ViewType.MARKDOWN)
        }, 'プレビュー');

        var submitButtonEl: any = React.createElement('button', {
            'onClick': this.handleSubmit.bind(this)
        }, '保存');

        var mainEl: any = React.createElement('div', {
                'className': 'carbon-components-editor'
            },
            this.state.currentViewType === ViewType.INPUT
                ? React.createElement(Input.Component, {
                    'text': this.state.text,
                    'onChange': this.handleInputText.bind(this)
                })
                : React.createElement(Preview.Component, {
                    'html': this.state.text
                }));

        return React.createElement('div', {},
            inputButtonEl, previewButtonEl, mainEl, submitButtonEl);
    }

    private handleChange(): void {
        this.setState(this.props.store.getState());
    }

    private handleSubmit(): void {
        this.props.actionCreator.submit(this.state.text);
    }

    private handleChangeView(nextViewType: ViewType): void {
        this.props.actionCreator.changeView(nextViewType, this.state.text);
    }

    private handleInputText(text: string): void {
        this.setState({ 'text': text });
    }
}
