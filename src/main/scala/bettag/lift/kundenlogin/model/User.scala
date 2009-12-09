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
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.sitemap.{Menu}
import net.liftweb.http.{S, SessionVar}
import net.liftweb.mapper.{KeyedMetaMapper}

import scala.util.matching.{Regex}
import scala.xml.{NodeSeq,Elem}

import bettag.lift.kundenlogin.lib._


class User extends LongKeyedMapper[User]
			  with MegaProtoUser[User] {
	def getSingleton = User

	/* We can't use CustomerFields here since it would be self-inheriting. */
	object customer extends MappedLongForeignKey(this, Customer) {
		override def dbNotNull_? = true
	}
		
	def logs: List[ActionLog] = ActionLog.findAll(By(ActionLog.user, this.id))
}


object User extends User
			   with MetaMegaProtoUser[User]
			   with UserStamped[User] {

	override def fieldOrder = List(id, firstName, lastName, email, locale, timezone, password)
}
