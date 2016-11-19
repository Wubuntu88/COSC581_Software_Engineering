//
//  ViewController.swift
//  KVO_Example
//
//  Created by William Edward Gillespie on 11/11/16.
//  Copyright © 2016 William Edward Gillespie. All rights reserved.
//

import UIKit

private var myContext = 0

class ViewController: UIViewController {
    
    @IBOutlet weak var dateLabel: UILabel!
    
    var myObject: MyClass! = MyClass()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Key value observing: Adding an Observer
        myObject.addObserver(self,
                             forKeyPath: #keyPath(MyClass.date),
                                options: [.new, .old],
                                context: &myContext)
    }
    
    //callbacks
    @IBAction func StartUpdatingDate(_ sender: UIButton) {
        myObject.startTimer()
    }
    @IBAction func stopUpdatingDate(_ sender: UIButton) {
        myObject.stopTimer()
    }
    
    //KVO overriding methods
    deinit {
        myObject.removeObserver(self, forKeyPath: #keyPath(MyClass.date), context: &myContext)
    }
    
    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        guard context == &myContext else {
            super.observeValue(forKeyPath: keyPath, of: object, change: change, context: context)
            return
        }
        
        // do something upon notification of the observed object
        if let newDate: NSDate = change?[.newKey] as? NSDate {
            print("new Date: \(newDate)")
            let s: String = "\(newDate)"
            
            let startIndex = s.index(s.startIndex, offsetBy: 0)
            let endIndex = s.index(s.startIndex, offsetBy: 19)
            let subStr: String = s[startIndex...endIndex]
            print("new Date: \(subStr)")
            dateLabel.text = "Date: \(subStr)"
        }
    }
    


}




















