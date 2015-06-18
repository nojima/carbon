import { ViewType } from '../../components/editor/ViewType';

export enum ActionType {
    CHANGE_VIEW,
    SUBMIT
}

export class Action {
    constructor(public type: ActionType) { }
}

export class ChangeViewAction extends Action {
    constructor(public nextViewType: ViewType, public html?: string) {
        super(ActionType.CHANGE_VIEW);
    }
}
