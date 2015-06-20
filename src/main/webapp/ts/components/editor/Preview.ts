import React = require('react');

export interface Props {
    html: string;
}

export class Component extends React.Component<Props, {}> {
    constructor(props: any, context: any) {
        super(props, context);
    }

    render(): React.ReactElement<any> {
        return React.createElement('div', {
            'className': 'carbon-components-editor-preview',
            'dangerouslySetInnerHTML': {'__html': this.props.html}
        });
    }
}
