//
//  MyClass.swift
//  KVO_Example
//
//  Created by William Edward Gillespie on 11/11/16.
//  Copyright © 2016 William Edward Gillespie. All rights reserved.
//

import Foundation

class MyClass: NSObject{
    dynamic var date: NSDate = NSDate()
    var timer: Timer?
    
    override init(){
        super.init()
    }
    
    func updateDate(){
        self.date = NSDate()
    }
    
    func startTimer(){
        if self.timer == nil{
            self.timer = Timer.scheduledTimer(timeInterval: 1.0, target: self, selector:#selector(MyClass.updateDate), userInfo: nil, repeats: true)
        }
    }
    
    func stopTimer(){
        self.timer?.invalidate()
        self.timer = nil
    }
}
