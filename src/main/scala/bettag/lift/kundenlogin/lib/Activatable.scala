/*
 * Copyright (c) 2009, Franz Bettag <franz@bett.ag>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
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
 * THIS SOFTWARE IS PROVIDED BY FRANZ BETTAG ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FRANZ BETTAG BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $Id: Publishable.scala,v a28fb7f91456 2009/08/09 02:35:24 franz $
 *
 */
package bettag.lift.kundenlogin.lib


import net.liftweb.mapper._
import java.util.Date

import bettag.lift.kundenlogin.model._

trait Activatable[A <: LongKeyedMapper[A] with IdPK with ActivateFields[A]]
extends KeyedMetaMapper[Long, A]
   with SkipLogging[A] {
	self: A with MetaMapper[A] with KeyedMapper[Long, A] =>

	override def afterCreate = (activeLog(_: A)) :: super.afterCreate
	override def afterUpdate = (activeLog(_: A)) :: super.afterUpdate
	override def afterDelete = (activeLog(_: A)) :: super.afterDelete

	private def activeLog(obj: A): Unit = {
		if (!self.skipLogging && obj.active.dirty_?) {
			val log = new ActionLog
			val action = if (obj.active) "activated" else "deactivated"
			log.action(action).klass(obj.getClass.toString).record(obj.id).save
		}
	}

}

trait ActivateFields[A <: Mapper[A]] {
	this: A =>

	private val thisTyped = this.asInstanceOf[A]

	object active extends MappedBoolean(thisTyped)
}