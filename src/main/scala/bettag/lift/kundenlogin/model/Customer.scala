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

import bettag.lift.kundenlogin.lib._
import bettag.lift.kundenlogin.model._

class Customer extends LongKeyedMapper[Customer]
				   with IdPK
				   with ActivateFields[Customer] {
	def getSingleton = Customer

	object name extends MappedString(this, 255) {
		override def dbNotNull_? = true
	}

	def users: List[User] = User.findAll(By(User.customer, this.id))

	def logs: List[ActionLog] = ActionLog.findAll(By(ActionLog.customer, this.id))

}

object Customer extends Customer
					with LongKeyedMetaMapper[Customer]
					with Stamped[Customer]
					with Activatable[Customer] {
	override def fieldOrder = List(name)

	override def dbIndexes = List(Index(IndexField(name)))
}
