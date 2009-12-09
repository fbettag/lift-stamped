/*
 *  Copyright (c) 2009, Franz Bettag <franz@bett.ag>
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * All advertising materials mentioning features or use of this software
 *       must display the following acknowledgement:
 *       This product includes software developed by the Bettag Systems
 *       and its contributors.
 *
 *  THIS SOFTWARE IS PROVIDED BY FRANZ BETTAG ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL FRANZ BETTAG BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  $Id$
 *
 */

package bettag.lift.kundenlogin.model

import net.liftweb.mapper._

import java.util.Date

import bettag.lift.kundenlogin.lib._
import bettag.lift.kundenlogin.model._

class ActionLog extends LongKeyedMapper[ActionLog]
				   with IdPK
				   with CustomerFields[ActionLog] {
	def getSingleton = ActionLog

	object action extends MappedString(this, 60) {
		override def dbNotNull_? = true
	}

	object klass extends MappedString(this, 60) {
		override def dbNotNull_? = true
		override def dbColumnName = "class"
	}

	object record extends MappedLong(this) {
		override def dbNotNull_? = true
	}

	object user extends MappedLongForeignKey(this, User) with LifecycleCallbacks {
		override def dbNotNull_? = true

		override def beforeCreate = this(User.currentUser)
	}

	object time extends MappedDateTime(this) with LifecycleCallbacks {
		override def dbNotNull_? = true

		override def beforeCreate = this(new Date)
	}

}

object ActionLog extends ActionLog
					with LongKeyedMetaMapper[ActionLog] {

	override def dbTableName = "logs"

	override def fieldOrder = List(action, klass, record, customer, user, time)

	override def dbIndexes = List(
		Index(IndexField(user), IndexField(time)),
		Index(IndexField(klass), IndexField(record)),
		Index(IndexField(klass), IndexField(record), IndexField(time)),
		Index(IndexField(klass), IndexField(record), IndexField(customer), IndexField(time))
	)
}