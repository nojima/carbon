import React = require('react');

export interface Props {
    text: string;
    onChange: any;
}

interface State {
    text: string;
}

export class Component extends React.Component<Props, State> {
    constructor(props: any, context: any) {
        super(props, context);
        this.state = {'text': this.props.text};
    }

    handleOnChange(e: any): void {
        var text: string = e.target.value;
        this.setState({'text': text});

        if (this.props.onChange) {
            this.props.onChange.call(undefined, text);
        }
    }

    render(): React.ReactElement<any> {
        var textareaEl: any = React.createElement('textarea', {
            'className': 'carbon-components-editor-input',

            'onChange': this.handleOnChange.bind(this),
            'value': this.state.text
        });
        return React.createElement('div', {}, textareaEl);
    }
}
