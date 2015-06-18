import { Dispatcher } from '../../dispatchers/editor/Dispatcher';
import { ViewType } from '../../components/editor/ViewType';
import { StoreEventType } from '../StoreEventType';
import { Action, ActionType, ChangeViewAction } from '../../actions/editor/Actions';
import EventEmitter3 = require('eventemitter3');

export class Store extends EventEmitter3 {
    private currentViewType: ViewType = ViewType.INPUT;

    private html: string = '';

    private dispatcher: Dispatcher;

    constructor(dispatcher: Dispatcher) {
        super();
        this.dispatcher = dispatcher;
        this.dispatcher.registerHandler(
            ActionType.CHANGE_VIEW, this.handleChangeView.bind(this));
    }

    getState(): { currentViewType: ViewType, html: string } {
        return {
            'currentViewType': this.currentViewType,
            'html': this.html
        };
    }

    private emitChange(): void {
        this.emit(StoreEventType.CHANGE);
    }

    private handleChangeView(action: Action): void {
        if (!(action instanceof ChangeViewAction)) {
            throw Error('instance of invalid type dispatched.');
        }
        var a: ChangeViewAction = <ChangeViewAction> action;
        this.currentViewType = a.nextViewType;
        this.html = a.html;
        this.emitChange();
    }
}
