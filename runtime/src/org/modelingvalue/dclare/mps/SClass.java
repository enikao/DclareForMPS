//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// (C) Copyright 2018 Modeling Value Group B.V. (http://modelingvalue.org)                                             ~
//                                                                                                                     ~
// Licensed under the GNU Lesser General Public License v3.0 (the "License"). You may not use this file except in      ~
// compliance with the License. You may obtain a copy of the License at: https://choosealicense.com/licenses/lgpl-3.0  ~
// Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on ~
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the  ~
// specific language governing permissions and limitations under the License.                                          ~
//                                                                                                                     ~
// Contributors:                                                                                                       ~
//     Wim Bast, Carel Bast, Tom Brus, Arjan Kok, Ronald Krijgsheld                                                    ~
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

package org.modelingvalue.dclare.mps;

import org.modelingvalue.collections.Set;
import org.modelingvalue.collections.util.Triple;
import org.modelingvalue.transactions.Constant;
import org.modelingvalue.transactions.Getable;

public class SClass {

    private static final Getable<Triple<Object, String, Set<SClass>>, SClass> DCLASS = Constant.of("DCLASS", p -> new SClass(p.a(), p.b(), p.c()));

    public static SClass of(Object id, String name, SClass... supers) {
        return DCLASS.get(Triple.of(id, name, Set.of(supers)));
    }

    private final Object      id;
    private final String      name;
    private final Set<SClass> supers;

    private SClass(Object id, String name, Set<SClass> supers) {
        super();
        this.id = id;
        this.name = name;
        this.supers = supers;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            SClass other = (SClass) obj;
            return id.equals(other.id);
        }
    }

    public boolean isAssignableFrom(SClass sub) {
        return equals(sub) || sub.supers.anyMatch(s -> isAssignableFrom(s));
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

}
