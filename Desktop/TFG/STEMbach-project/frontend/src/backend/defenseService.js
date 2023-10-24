import {config, appFetch} from "./appFetch";

export const createDefense = (formData, onSuccess, onErrors) =>
    appFetch('/defenses/create', config("POST", formData), onSuccess, onErrors);
