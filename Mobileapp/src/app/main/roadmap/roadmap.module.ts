import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RoadmapPageRoutingModule } from './roadmap-routing.module';

import { RoadmapPage } from './roadmap.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RoadmapPageRoutingModule
  ],
  declarations: [RoadmapPage]
})
export class RoadmapPageModule {}
