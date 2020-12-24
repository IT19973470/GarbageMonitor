import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {MainPage} from './main.page';

const routes: Routes = [
    {
        path: '',
        redirectTo: 'roadmap',
        pathMatch: 'full'
    },
    {
        path: '',
        component: MainPage,
        children:[
            {
                path: 'roadmap',
                loadChildren: () => import('./roadmap/roadmap.module').then(m => m.RoadmapPageModule),
                // data: {
                //     preload: false
                // },
            }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule],
})
export class MainPageRoutingModule {
}
