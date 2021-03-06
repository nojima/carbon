import Flux = require('flux');
import { Action, ActionType } from '../../actions/editor/Actions';

export class Dispatcher extends Flux.Dispatcher<Action> {
    constructor() {
        super();
    }

    registerHandler(type: ActionType, handler: Function, context?: Object): void {
        this.register(function(action: Action): void {
            if (action.type === type) {
                handler.call(context, action);
            }
        });
    }
}
