import { Promise } from 'es6-promise';

export function requestJson<T>(
        url: string, method: string, json: Object): Promise<T> {
    'use strict';

    return new Promise<T>((resolve: Function, reject: Function): void => {
        let xhr: XMLHttpRequest = new XMLHttpRequest();
        xhr.onreadystatechange = (): void => {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    let json: any = JSON.parse(xhr.responseText);
                    if (json.success) {
                        resolve(<T>json.result);
                    } else {
                        reject('Request failed');
                    }
                } else {
                    reject('XHR Error');
                }
            }
        };
        xhr.open(method, url);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(json));
    });
}
